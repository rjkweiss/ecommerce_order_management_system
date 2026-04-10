# ShopSmart: E-Commerce Order Management System

## Overview
[1-2 paragraphs: What does this system do? What problem does it solve? What are the key capabilities?]

## How to Build and Run

### Prerequisites
- Java [version]
- [Build tool / IDE]

### Build
[Build commands or instructions]

### Run
[Example CLI command with arguments]

### Run Tests
[How to run the test suite]

---

## Architecture Overview
[High-level description of the system's structure — how data flows from CSV input through processing to output. Reference your UML diagram here.]

---

## Phase 1: Product Catalog

### Design Decisions

#### Product Hierarchy
[Explain IProduct → AbstractProduct → concrete classes. Why this structure? What does each layer provide?]

#### Builder Pattern
[Why Builder over telescoping constructors? Why one Builder per concrete class instead of a single universal Builder? Where does validation live and why?]

#### Factory Method
[Why Factory Method over Abstract Factory? What is the factory's single responsibility? How does it interact with the Builders?]

#### Parser and Loader Separation
[Why is CatalogParser separate from CatalogLoader? What does each one own? How does this support SRP?]

### Enums
[List your enums and explain why enums were the right choice (fixed sets, behavior on enum via fields/methods). Highlight BillingCycle as an example of an enum with behavior.]

---

## Phase 2: Shopping Cart & Pricing
[To be completed]

### Design Decisions

#### ShoppingCart<T> — Generics and Iteration
[Explain bounded type parameter, custom Iterator, why Iterable]

#### Strategy Pattern — Pricing
[Why Strategy? What varies? How are strategies swapped at runtime?]

#### Decorator Pattern — Add-Ons
[Why Decorator over inheritance? Combinatorial explosion argument. How decorators compose.]

---

## Phase 3: Order Processing
[To be completed]

### Design Decisions

#### Order Lifecycle
[OrderStatus enum with transition validation]

#### Processing Pipeline
[How stages are structured, extensibility]

#### Fulfillment Hierarchy
[Polymorphic dispatch over instanceof]

---

## Phase 4: Reporting & Output
[To be completed]

### Design Decisions

#### Comparable vs Comparator
[Natural ordering vs custom orderings]

#### Report Rendering Strategy
[Output format as a swappable strategy]

---

## Phase 5: CLI & Integration
[To be completed]

---

## SOLID Principles Mapping

For each principle, cite **at least two** specific design decisions from the codebase.

### Single Responsibility Principle (SRP)
1. [Design decision + explanation]
2. [Design decision + explanation]

### Open/Closed Principle (OCP)
1. [Design decision + explanation]
2. [Design decision + explanation]

### Liskov Substitution Principle (LSP)
1. [Design decision + explanation]
2. [Design decision + explanation]

### Interface Segregation Principle (ISP)
1. [Design decision + explanation]
2. [Design decision + explanation]

### Dependency Inversion Principle (DIP)
1. [Design decision + explanation]
2. [Design decision + explanation]

---

## Design Patterns Summary

| Pattern | Where Used | Justification |
|---------|-----------|---------------|
| Builder | [Location] | [Why this pattern fits] |
| Factory Method | [Location] | [Why this pattern fits] |
| Strategy | [Location] | [Why this pattern fits] |
| Decorator | [Location] | [Why this pattern fits] |
| Iterator | [Location] | [Why this pattern fits] |
| Observer | [Location] | [Why this pattern fits] |

---

## Testing Strategy
[Describe your overall testing approach — what do you test, how do you organize tests, what edge cases do you prioritize? Mention coverage targets.]

---

## Known Limitations / Future Improvements
[What would you change with more time? Any design debt you're aware of?]

---

## Project Structure
```
shopsmart/
├── src/
│   ├── model/
│   │   ├── product/
│   │   ├── cart/
│   │   ├── order/
│   │   └── pricing/
│   ├── pipeline/
│   ├── reporting/
│   ├── io/
│   ├── cli/
│   └── Main.java
├── test/
├── data/
└── README.md
```
