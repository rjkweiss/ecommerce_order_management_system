# ShopSmart: E-Commerce Order Management System

## Project Overview

ShopSmart is the backend engine for a command-line e-commerce order management system. 
The system reads product catalogs from CSV files, processes orders through configurable business rules, and generates output in multiple formats. 
The project is structured in phases, each layering new OOD concepts onto a stable foundation.

**Current Status: Phase 1 — Product Catalog (Complete)**

---

## Phase 1: Product Catalog

### Architecture Overview

Phase 1 establishes the domain model for ShopSmart's product catalog. 
The system supports three product types: physical goods, digital products and subscriptions, 
loaded from a CSV file and stored in an immutable, ID-indexed catalog.

**Core flow:** `CSV File` → `CatalogParser` → `ProductFactory` → `CatalogLoader` → `Map<String, IProduct>`

### Class Structure

#### Product Hierarchy

```
IProduct (interface)
  └── AbstractProduct (abstract class)
        ├── PhysicalProduct
        ├── DigitalProduct
        └── SubscriptionProduct
```

- **`IProduct`** -- Defines the contract: `isAvailable()`, `matches(String searchTerm)`, and getter methods. 
Every component that works with products depends on this interface, never on concrete types.

- **`AbstractProduct`** -- Holds shared state (`id`, `name`, `basePrice`, `category`) and shared behavior (`matches()` with a `findSearchTerm()` helper, `equals()`/`hashCode()` based on shared fields). 
Constructor is `protected` to enforce that products are only created through their Builders.

- **Concrete products** -- Each adds type-specific fields and overrides `matches()` to search across its own relevant fields via `super.matches()` + additional field checks. 
Each is fully immutable after construction.

#### Type-Specific Fields

| PhysicalProduct | DigitalProduct | SubscriptionProduct |
|----------------|----------------|---------------------|
| weight (lbs) | fileSize (MB) | monthlyPrice |
| warehouseLocation (enum) | downloadUrl | billingCycle (enum) |
| fragile (boolean) | licenseType (enum) | autoRenew (boolean) |
| | | tier (enum) |

**`SubscriptionProduct` pricing:** `monthlyPrice` is the stored constant; `basePrice` is derived in the constructor based on `billingCycle` (monthly = 1×, quarterly = 3×, annual = 12×). 
This reflects real-world behavior - the advertised rate doesn't change, but the per-cycle charge does.

#### Builders (Nested Static Inner Classes)

Each concrete product contains a nested `Builder` class. Builders handle type-specific field validation and default values. 
Shared required-field validation (`id`, `name`, `category`, `basePrice`) lives in `AbstractProduct`'s constructor, since every Builder must call `super()`. 
This eliminates duplicated validation logic across three Builders (DRY).

Default values for optional fields are set in the Builder (e.g., `DigitalProduct.Builder` defaults `downloadUrl` to `""`, `licenseType` to `SINGLE_USE`), which also eliminates null-safety concerns downstream.

#### Catalog Loading Pipeline

- **`CatalogParser`** -- Reads a CSV file line by line using `line.split("\\s*,\\s*")`. 
Maps each row's values to a `Map<String, String>` keyed by header names. 
Returns `List.copyOf()` for immutability.

- **`ProductFactory`** -- Static `createProduct(Map<String, String>)` method. 
Converts the `type` field to a `ProductType` enum via `valueOf()`, then switches on it to route to the appropriate Builder. 
Contains private helper methods (`parseDouble`, `parseEnum`, `parseBoolean`, `parseString`) that handle empty strings, defaults, and type conversion uniformly.

- **`CatalogLoader`** -- Orchestrates the flow: takes a `CatalogParser`, calls `parseCatalog()`, iterates over rows, delegates to `ProductFactory`, and collects results into a `Map<String, IProduct>`. 
Returns `Map.copyOf()` for immutability.

#### Enums

- **`ProductType`** -- `PHYSICAL`, `DIGITAL`, `SUBSCRIPTION`. 
Used by `ProductFactory` for switch-based routing.
- **`WarehouseLocation`** -- Fixed set of warehouse identifiers for physical products.
- **`LicenseType`** -- `SINGLE_USE`, `MULTI_DEVICE`, `ENTERPRISE`.
- **`BillingCycle`** -- `MONTHLY`, `QUARTERLY`, `ANNUAL`.
- **`SubscriptionTier`** -- `BASIC`, `PREMIUM`, `ENTERPRISE`.

Enums are used for all fixed sets of values. This provides compile-time safety and eliminates invalid-string bugs.

---

### Design Decisions

#### Why `HashMap` for the Catalog?

The catalog is keyed by product ID (String), and the primary access pattern is lookup by ID. 
`HashMap` provides O(1) average-case lookup. The catalog is wrapped in `Map.copyOf()` on return, making it unmodifiable.

#### Why Nested Builders Instead of an Abstract Builder?

Each product type has different optional fields with different types and defaults. 
An abstract `BaseBuilder` would need to be generic and would add complexity without meaningful deduplication - 
the shared validation already lives in `AbstractProduct`'s constructor. 
Keeping Builders nested in their concrete classes co-locates the construction logic with the class it builds.

#### Why Static Factory Method on `ProductFactory`?

`ProductFactory.createProduct()` is the single point in the system that knows about concrete product types. 
No other class references `PhysicalProduct`, `DigitalProduct`, or `SubscriptionProduct` directly. 
This isolates the concrete types behind the factory, so adding a new product type only requires changes in one place.

#### Why Parsing Helpers in `ProductFactory`?

The `parseDouble`, `parseEnum`, `parseBoolean`, and `parseString` helpers centralize the conversion from raw CSV strings to typed values. 
Each handles empty/null strings with sensible defaults, keeping the factory's switch cases clean and consistent. 
This avoids scattered `try-catch` blocks and duplicated null checks.

---

### SOLID Principles Mapping

| Principle | How It's Applied |
|-----------|-----------------|
| **Single Responsibility** | `CatalogParser` only parses CSV structure. `ProductFactory` only maps data to products. `CatalogLoader` only orchestrates the pipeline. Each Builder only validates and constructs its product type. |
| **Open/Closed** | Adding a new product type (e.g., `BundleProduct`) requires a new concrete class + Builder and a new case in `ProductFactory` — existing product classes are untouched. |
| **Liskov Substitution** | Any `IProduct` can be used interchangeably. `matches()` and `isAvailable()` behave correctly regardless of concrete type. Client code never needs to know which product type it's working with. |
| **Interface Segregation** | `IProduct` defines only the methods that all consumers need. Product-specific methods (e.g., `getWeight()`) live on the concrete classes, not the interface. |
| **Dependency Inversion** | `CatalogLoader` depends on `CatalogParser` (which could be abstracted to an interface). All catalog consumers depend on `IProduct`, never on concrete product classes. |

---

### Design Patterns Used

| Pattern | Where | Why |
|---------|-------|-----|
| **Builder** | `PhysicalProduct.Builder`, `DigitalProduct.Builder`, `SubscriptionProduct.Builder` | Products have many fields (required + optional) and must be immutable after construction. Builders provide a fluent API, handle defaults, and enforce validation before the object is created. |
| **Factory Method** | `ProductFactory.createProduct()` | Decouples the CSV parsing layer from concrete product types. The factory encapsulates the decision of which class to instantiate based on the `type` field, so the rest of the system works only with `IProduct`. |

---

### Exception Strategy

- **"Throw early, catch late, catch specifically"** -- Validation happens at the earliest point that has sufficient context 
(Builder for type-specific fields, `AbstractProduct` constructor for shared fields, `ProductFactory` helpers for parsing errors).
- **Checked exceptions** for business logic failures (`InvalidCSVFileException`) -- these represent recoverable conditions that 
callers must handle.
- **Unchecked exceptions** (`IllegalArgumentException`) for programming errors like null IDs or negative prices - these indicate bugs, not runtime conditions.

---

### Testing Strategy

- Product Builders tested for both valid construction and invalid input rejection
- `ProductFactory` tested for each product type, unknown types, and malformed data
- `CatalogParser` tested for well-formed CSV, empty files, and structural errors
- `matches()` tested per product type to verify polymorphic field searching
- `equals()`/`hashCode()` tested per course convention
- Getters tested implicitly through parser and factory tests

---

### Project Structure (Phase 1)

```
shopsmart/
├── src/
│   └── productCatalog/
│       ├── IProduct.java
│       ├── AbstractProduct.java
│       ├── PhysicalProduct.java        (+ nested Builder)
│       ├── DigitalProduct.java         (+ nested Builder)
│       ├── SubscriptionProduct.java    (+ nested Builder)
│       ├── ProductFactory.java
│       ├── ProductType.java            (enum)
│       ├── WarehouseLocation.java      (enum)
│       ├── LicenseType.java            (enum)
│       ├── BillingCycle.java           (enum)
│       ├── SubscriptionTier.java       (enum)
│       ├── CatalogParser.java
│       ├── CatalogLoader.java
│       └── InvalidCSVFileException.java
├── test/
│   └── productCatalog/
│       ├── PhysicalProductTest.java
│       ├── DigitalProductTest.java
│       ├── SubscriptionProductTest.java
│       ├── ProductFactoryTest.java
│       └── CatalogParserTest.java
└── resources/
    └── catalog.csv
```

---

### Upcoming: Phase 2 — Shopping Cart & Pricing

Phase 2 introduces `ShoppingCart<T extends IProduct>` with generics, a custom `Iterator`, the Strategy pattern for pricing, and the Decorator pattern for cart add-ons. The goal is to extend Phase 1's foundation without modifying any existing classes — OCP in practice.

---

**Author:** Joyce — CS5004, Object-Oriented Design  
**Tools:** Java 21, JUnit 5, IntelliJ IDEA, draw.io (UML)
