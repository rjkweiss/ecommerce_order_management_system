package productCatalog;

import java.util.Objects;

/**
 * This class represents a physical product in the ecommerce order management system. It uses a
 * custom Builder to handle required and optional parameters that are passed in when a product is
 * being created.
 */
public class PhysicalProduct extends AbstractProduct {
  private final double weight;
  private final String warehouseLocation;
  private final boolean fragile;


  /**
   * Constructor, creates a Physical Product using a builder
   * @param builder the physical product builder, as Builder
   */
  private PhysicalProduct(Builder builder) {
    super(builder.id, builder.name, builder.basePrice, builder.category);
    this.weight = builder.weight;
    this.warehouseLocation = builder.warehouseLocation;
    this.fragile = builder.fragile;
  }

  /**
   * Getter for weight
   * @return product weight, as double
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Getter for warehouse location
   * @return warehouse location, as String
   */
  public String getWarehouseLocation() {
    return warehouseLocation;
  }

  /**
   * Getter for fragile
   * @return true if product is fragile, else false
   */
  public boolean isFragile() {
    return fragile;
  }

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public boolean matches(String searchTerm) {
    return super.matches(searchTerm) || findSearchTerm(warehouseLocation, searchTerm);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    PhysicalProduct that = (PhysicalProduct) o;
    return Double.compare(weight, that.weight) == 0 && fragile == that.fragile
        && Objects.equals(warehouseLocation, that.warehouseLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), weight, warehouseLocation, fragile);
  }

  @Override
  public String toString() {
    return "PhysicalProduct{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", basePrice=" + basePrice +
        ", category='" + category + '\'' +
        ", weight=" + weight +
        ", fragile=" + fragile +
        ", warehouseLocation='" + warehouseLocation + '\'' +
        '}';
  }

  // -------------------------------------- Builder class --------------------------------------- //

  /**
   * Custom builder for a physical product. It creates a physical product object with all required
   * fields and also allows for additional / optional parameters to be provided.
   */
  public static class Builder {
    private String id;
    private String name;
    private double basePrice;
    private String category;

    // optional
    private double weight = 0.0;
    private String warehouseLocation = "";
    private boolean fragile = false;

    /**
     * Constructor, creates Physical Product Builder with required fields
     * @param id the product id, as String
     * @param name the name of product, as String
     * @param category the category of product, as String
     * @param basePrice the base price of Product, as double
     */
    public Builder(String id, String name, double basePrice, String category) {
      this.id = id;
      this.name = name;
      this.basePrice = basePrice;
      this.category = category;
    }

    /**
     * fluent setter for product weight
     * @param weight product weight, as double
     * @return this physical product builder, as Builder
     */
    public Builder weight(double weight) {
      this.weight = weight;
      return this;
    }

    /**
     * fluent setter for product warehouse location
     * @param warehouseLocation product warehouse location, as String
     * @return this physical product builder, as Builder
     */
    public Builder warehouseLocation(String warehouseLocation) {
      this.warehouseLocation = warehouseLocation;
      return this;
    }

    /**
     * fluent setter for whether product fragile
     * @param fragile boolean flag indicating product's fragility, as boolean
     * @return boolean flag
     */
    public Builder fragile(boolean fragile) {
      this.fragile = fragile;
      return this;
    }

    /**
     * Builder method that creates a new instance of Physical Product Object
     * @return a new Physical Product Object, as PhysicalProduct
     */
    public PhysicalProduct build() {
      return new PhysicalProduct(this);
    }
  }
}
