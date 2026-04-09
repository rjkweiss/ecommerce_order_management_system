package productCatalog;

import java.util.Objects;

/**
 * This is a concrete class representing Digital Products in the E-Commerce Order Management
 * System. A Builder is used for creating product so that it can handle optional parameters
 * that might be passed into the system.
 */
public class DigitalProduct extends AbstractProduct {
  private final double fileSize;
  private final String downloadUrl;
  private final LicenseType licenseType;

  /**
   * Constructor, creates a digital product using Builder
   * @param builder digital product builder, as Builder custom data type
   */
  private DigitalProduct(Builder builder) {
    super(builder.id, builder.name, builder.basePrice, builder.category);

    this.fileSize = builder.fileSize;
    this.downloadUrl = builder.downloadUrl;
    this.licenseType = builder.licenseType;
  }

  /**
   * Getter for file size
   * @return file size, as double
   */
  public double getFileSize() {
    return fileSize;
  }

  /**
   * Getter for download url
   * @return download url, as String
   */
  public String getDownloadUrl() {
    return downloadUrl;
  }

  /**
   * Getter for license type
   * @return license type, as custom LicenseType enum data type
   */
  public LicenseType getLicenseType() {
    return licenseType;
  }

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public boolean matches(String searchTerm) {
    return super.matches(searchTerm) || findSearchTerm(downloadUrl, searchTerm) || findSearchTerm(
        String.valueOf(licenseType), searchTerm);
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
    DigitalProduct that = (DigitalProduct) o;
    return Double.compare(fileSize, that.fileSize) == 0 && Objects.equals(
        downloadUrl, that.downloadUrl) && licenseType == that.licenseType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), fileSize, downloadUrl, licenseType);
  }

  @Override
  public String toString() {
    return "DigitalProduct{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", basePrice=" + basePrice +
        ", category='" + category + '\'' +
        ", fileSize=" + fileSize +
        ", downloadUrl='" + downloadUrl + '\'' +
        ", licenseType=" + licenseType +
        '}';
  }

  // -------------------------------------- Builder class --------------------------------------- //

  /**
   * Nested builder class for digital product. Creates a Digital Product with all required fields
   * and handles any optional fields that this class have.
   */
  public static class Builder {

    // required fields
    private final String id;
    private final String name;
    private final double basePrice;
    private final String category;

    // optional fields
    private double fileSize = 0.0;
    private String downloadUrl = "";
    private LicenseType licenseType = LicenseType.SINGLE_USE;

    /**
     * Constructor, creates Digital Product Builder with required fields
     * @param id the product id, as String
     * @param name the name of product, as String
     * @param category the category of product, as String
     * @param basePrice the base price of Product, as double
     */
    public Builder(String id, String name, String category, double basePrice) {
      this.id = id;
      this.name = name;
      this.category = category;
      this.basePrice = basePrice;
    }

    /**
     * fluent setter for file size
     * @param fileSize the file size, as double
     * @return this digital product builder, as Builder
     */
    public Builder fileSize(double fileSize) {
      this.fileSize = fileSize;
      return this;
    }

    /**
     * fluent setter for download url
     * @param downloadUrl the download url, as String
     * @return this digital product builder, as Builder
     */
    public Builder downloadUrl(String downloadUrl) {
      this.downloadUrl = downloadUrl;
      return this;
    }

    /**
     * fluent setter for license type
     * @param licenseType the license type, as custom LicenseType data type
     * @return this digital product builder, as Builder
     */
    public Builder licenseType(LicenseType licenseType) {
      this.licenseType = licenseType;
      return this;
    }

    /**
     * Builder method to create the final digital product
     * @return the digital product instance, as DigitalProduct
     */
    public DigitalProduct build() {
      return new DigitalProduct(this);
    }
  }
}
