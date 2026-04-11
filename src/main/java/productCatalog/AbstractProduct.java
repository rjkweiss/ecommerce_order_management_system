package productCatalog;

import java.util.Objects;

/**
 * Abstract Product class that holds shared values across different concrete
 * implementations of product. Each product has a unique ID, a name, base price and category.
 */
public abstract class AbstractProduct implements IProduct {
  protected final String id;
  protected final String name;
  protected final double basePrice;
  protected final String category;

  /**
   * Constructor, creates a product with ID, name, base price and category
   * @param id the unique id of product, as String
   * @param name the name of product, as String
   * @param basePrice the base price of product, as double
   * @param category the category of product, as String
   * @throws IllegalArgumentException if any of the arguments are invalid
   */
  protected AbstractProduct(String id, String name, double basePrice, String category) throws IllegalArgumentException {
    this.validateId(id);
    this.validateName(name);
    this.validateCategory(category);
    this.validateBasePrice(basePrice);

    this.id = id;
    this.name = name;
    this.basePrice = basePrice;
    this.category = category;
  }

  /**
   * Getter for product's ID
   * @return id, as String
   */
  @Override
  public String getID() {
    return id;
  }

  /**
   * Getter for product's name
   * @return name, as String
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Getter for product's base price
   * @return base price, as double
   */
  @Override
  public double getBasePrice() {
    return basePrice;
  }

  /**
   * Getter for product's category
   * @return category, as String
   */
  @Override
  public String getCategory() {
    return category;
  }

  @Override
  public boolean matches(String searchTerm) {
    return findSearchTerm(name, searchTerm) || findSearchTerm(category, searchTerm);
  }

  protected boolean findSearchTerm(String state, String searchTerm) {
    String normalizedSearchTerm = searchTerm.toLowerCase();
    return state.toLowerCase().contains(normalizedSearchTerm);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractProduct that = (AbstractProduct) o;
    return Double.compare(basePrice, that.basePrice) == 0 && Objects.equals(id,
        that.id) && Objects.equals(name, that.name) && Objects.equals(category,
        that.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, basePrice, category);
  }

  // ------------------------------------ Helper methods ---------------------------------------- //

  /**
   * Validates that product id provided is valid
   * @param id the product id, as String
   * @throws IllegalArgumentException if id is null or blank
   */
  private void validateId(String id) throws IllegalArgumentException {
    if (id == null || id.isBlank())
      throw new IllegalArgumentException("id cannot be null or blank");

  }

  /**
   * Validates that product name is valid
   * @param name the product name, as String
   * @throws IllegalArgumentException if name is null or blank
   */
  private void validateName(String name) throws IllegalArgumentException {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("name cannot be null or blank");
  }

  /**
   * Validates that product category is valid
   * @param category the product category, as String
   * @throws IllegalArgumentException category is null or blank
   */
  private void validateCategory(String category) throws IllegalArgumentException {
    if (category == null || category.isBlank())
      throw new IllegalArgumentException("category cannot be null or blank");
  }

  /**
   * Validates that product base price is valid
   * @param basePrice the product base price, as double
   * @throws IllegalArgumentException if base price is negative
   */
  private void validateBasePrice(double basePrice) throws IllegalArgumentException {
    if (basePrice < 0.0)
      throw new IllegalArgumentException("base price cannot be negative");
  }
}
