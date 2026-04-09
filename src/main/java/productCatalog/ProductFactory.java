package productCatalog;

import java.util.Map;

/**
 * This class is a factory Method class that allows us to create the three types of
 * products that the ecommerce order management system supports.
 */
public class ProductFactory {

  /**
   * Creates a product from a row of product data parsed from csv file
   * @param row a map of product data, as Map<String, String>
   * @return a Product instance whose type is the high order Interface (parent type)
   */
  public static IProduct createProduct(Map<String, String> row) {

    // holds the product we created
    IProduct product;

    // get the type of product
    ProductType type = parseEnum(row.get("type"), ProductType.class, ProductType.PHYSICAL);

    // get the shared fields across multiple product types
    String name = parseString(row.get("name"));
    String id = parseString(row.get("id"));
    String category = parseString(row.get("category"));
    double basePrice = parseDouble(row.get("basePrice"));

    // create product base on the type of the product in the csv row
    switch (type) {
      case ProductType.PHYSICAL:
        String location = parseString(row.get("location"));
        double weight = parseDouble(row.get("weight"));
        boolean fragile = parseBoolean(row.get("fragile"));

        product = new PhysicalProduct.Builder(id, name, basePrice, category)
            .weight(weight)
            .warehouseLocation(location)
            .fragile(fragile)
            .build();
        break;

      case ProductType.DIGITAL:
        double fileSize = parseDouble(row.get("fileSize"));
        String downloadUrl = parseString(row.get("downloadUrl"));
        LicenseType licenseType = parseEnum(row.get("licenseType"), LicenseType.class, LicenseType.SINGLE_USE);

        product = new DigitalProduct.Builder(id, name, category, basePrice)
            .fileSize(fileSize)
            .downloadUrl(downloadUrl)
            .licenseType(licenseType)
            .build();
        break;

      case ProductType.SUBSCRIPTION:
        double monthlyPrice = parseDouble(row.get("monthlyPrice"));
        boolean autoRenew = parseBoolean(row.get("autoRenew"));
        BillingCycle billingCycle = parseEnum(row.get("billingCycle"), BillingCycle.class, BillingCycle.MONTHLY);
        Tier tier = parseEnum(row.get("tier"), Tier.class, Tier.BASIC);

        product = new SubscriptionProduct.Builder(id, name, category).autoRenew(autoRenew)
            .billingCycle(billingCycle)
            .monthlyPrice(monthlyPrice)
            .tier(tier)
            .build();
        break;

      default:
        throw new IllegalArgumentException("Unknown product type: " + type);
    }

    return product;
  }

  // ---------------------------------- Helper method ------------------------------------------- //

  /**
   * Parses double data type fields. If value is not present, default to 0
   * @param value the value of field, as String
   * @return actual double value of a field, as double
   */
  private static double parseDouble(String value) {
    if (value == null || value.isBlank()) return 0;
    return Double.parseDouble(value);
  }

  /**
   * Parses boolean data types. If value is not present, default to false
   * @param value the value of field, as String
   * @return a boolean value of field
   */
  private static boolean parseBoolean(String value) {
    if (value == null || value.isBlank()) return false;
    return value.equalsIgnoreCase("true");
  }

  /**
   * Parses enum data types. If value is not present, default to one of the enum constants.
   * @param value the value of field, as String
   * @param enumClass the enum whose field this belongs to, as a generic
   * @param defaultValue the default value from enum to use as default, as generic
   * @return a generic enum type for this field
   * @param <T> generic parameter, representing different enum
   */
  private static<T extends Enum<T>> T parseEnum(String value, Class<T> enumClass, T defaultValue) {
    if (value == null || value.isBlank()) return defaultValue;
    return Enum.valueOf(enumClass, value.toUpperCase());
  }

  /**
   * Parses a String data type. If value is missing, default to ""
   * @param value the value of field, as String
   * @return field value, as String
   */
  private static String parseString(String value) {
    if (value == null || value.isBlank()) return "";
    return value;
  }
}
