package productCatalog;

/**
 * This enum represents Billing Cycles options supported by the ecommerce order management
 * System
 */
public enum BillingCycle {
  MONTHLY(1), QUARTERLY(3), ANNUAL(12);

  private final int months;

  /**
   * Constructor for billing cycle
   * @param months the number of months in each billing cycle
   */
  BillingCycle(int months) {
    this.months = months;
  }

  /**
   * Calculates Base Pay given the billing cycle
   * @param monthlyPrice the monthly price, as double
   * @return base pay, as double
   */
  public double calculateBasePrice(double monthlyPrice) {
    return monthlyPrice * months;
  }
}
