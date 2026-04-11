package productCatalog;

import java.util.Objects;

/**
 * This class represents a Subscription Product in the ecommerce order management system.
 * Uses a Builder to handle both required and optional parameters
 */
public class SubscriptionProduct extends AbstractProduct {

  private final double monthlyPrice;
  private final BillingCycle billingCycle;
  private final boolean autoRenew;
  private final Tier tier;

  /**
   *
   * @param builder
   */
  private SubscriptionProduct(Builder builder) {
    super(builder.id, builder.name, builder.basePrice, builder.category);
    this.monthlyPrice = builder.monthlyPrice;
    this.billingCycle = builder.billingCycle;
    this.autoRenew = builder.autoRenew;
    this.tier = builder.tier;
  }

  public double getMonthlyPrice() {
    return monthlyPrice;
  }

  public BillingCycle getBillingCycle() {
    return billingCycle;
  }

  public boolean isAutoRenew() {
    return autoRenew;
  }

  public Tier getTier() {
    return tier;
  }

  @Override
  public ProductType getProductType() {
    return ProductType.SUBSCRIPTION;
  }

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public boolean matches(String searchTerm) {
    return super.matches(searchTerm) ||
        findSearchTerm(String.valueOf(billingCycle), searchTerm) ||
        findSearchTerm(String.valueOf(tier), searchTerm);
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
    SubscriptionProduct that = (SubscriptionProduct) o;
    return Double.compare(monthlyPrice, that.monthlyPrice) == 0
        && autoRenew == that.autoRenew && billingCycle == that.billingCycle && tier == that.tier;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), monthlyPrice, billingCycle, autoRenew, tier);
  }

  @Override
  public String toString() {
    return "SubscriptionProduct{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", basePrice=" + basePrice +
        ", category='" + category + '\'' +
        ", tier=" + tier +
        ", billingCycle=" + billingCycle +
        ", monthlyPrice=" + monthlyPrice +
        ", autoRenew=" + autoRenew +
        '}';
  }

  // -------------------------------------- Builder class --------------------------------------- //
  public static class Builder {
    private String id;
    private String name;
    private double basePrice;
    private String category;

    // optional
    private double monthlyPrice;
    private BillingCycle billingCycle = BillingCycle.MONTHLY;
    private boolean autoRenew = true;
    private Tier tier = Tier.BASIC;

    /**
     * Constructor, creates Digital Product Builder with required fields
     * @param id the product id, as String
     * @param name the name of product, as String
     * @param category the category of product, as String
     */
    public Builder(String id, String name, String category) {
      this.id = id;
      this.name = name;
      this.category = category;
    }

    /**
     * fluent setter for monthly price
     * @param monthlyPrice the product's monthly price, as double
     * @return this subscription builder, as Builder
     */
    public Builder monthlyPrice(double monthlyPrice) {
      this.monthlyPrice = monthlyPrice;
      return this;
    }

    /**
     * fluent setter for billing cycle
     * @param billingCycle the product's billing cycle, as custom Billing Cycle
     * @return this subscription builder, as Builder
     */
    public Builder billingCycle(BillingCycle billingCycle) {
      this.billingCycle = billingCycle;
      return this;
    }

    /**
     * fluent setter for auto-renew flag
     * @param autoRenew the product's auto-renew flag, as boolean
     * @return this subscription builder, as Builder
     */
    public Builder autoRenew(boolean autoRenew) {
      this.autoRenew = autoRenew;
      return this;
    }

    /**
     * fluent setter for subscription tier
     * @param tier the product tier, as custom Tier
     * @return this subscription builder, as Builder
     */
    public Builder tier(Tier tier) {
      this.tier = tier;
      return this;
    }

    /**
     * Builder method that creates an instance of Subscription Product
     * @return a new Subscription Product Object, as SubscriptionProduct
     */
    public SubscriptionProduct build() {
      // compute the basePrice
      basePrice = billingCycle.calculateBasePrice(monthlyPrice);
      return new SubscriptionProduct(this);
    }
  }
}
