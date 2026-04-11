package productCatalog;

/**
 * This interface represents a product in the catalog system. There are 3 types of products:
 * Subscription Product, Digital Product, PhysicalbProduct
 */
public interface IProduct {

  /**
   * Checks if product is available
   * @return true if product is available, false otherwise
   */
  boolean isAvailable();

  /**
   * Checks if search term appears anywhere in the product
   * @param searchTerm the search term to find, as String
   * @return true if search term is found, false otherwise
   */
  boolean matches(String searchTerm);

  /**
   * Returns the id of product
   * @return id, as String
   */
  String getID();

  /**
   * Returns the name of product
   * @return name, as String
   */
  String getName();

  /**
   * Returns the base price of product
   * @return base price, as double
   */
  double getBasePrice();

  /**
   * Returns the category for product
   * @return category, as String
   */
  String getCategory();

  /**
   * Returns the type of product
   * @return product type, as custom ProductTypeEnum
   */
  ProductType getProductType();
}
