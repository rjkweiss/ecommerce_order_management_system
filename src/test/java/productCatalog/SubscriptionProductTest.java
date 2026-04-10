package productCatalog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionProductTest {
  private SubscriptionProduct subscriptionProduct;

  @BeforeEach
  void setUp() {
    // set up with all fields
    subscriptionProduct = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.MONTHLY)
        .monthlyPrice(24.99)
        .tier(Tier.PREMIUM)
        .build();
  }

  @Test
  void testSetup_requiredFields() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix",
        "Streaming").build();
  }

  @Test
  void testSetup_invalidRequiredFields() {
    assertThrows(IllegalArgumentException.class, () -> {
      new SubscriptionProduct.Builder("1002", "Netflix", null).build();
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new SubscriptionProduct.Builder("1002", "", "streaming").build();
    });
  }

  @Test
  void testSetup_someOptionalFields() {
    SubscriptionProduct other =  new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.MONTHLY)
        .build();

    // test defaults
    assertEquals(0, other.getMonthlyPrice());
    assertEquals(0, other.getBasePrice());
    assertTrue(other.isAutoRenew());
    assertEquals(Tier.BASIC, other.getTier());
  }

  @Test
  void testSetup_invalidOptionalFields() {}

  @Test
  void getMonthlyPrice() {
    assertEquals(24.99, subscriptionProduct.getMonthlyPrice());
  }

  @Test
  void getBillingCycle() {
    assertEquals(BillingCycle.MONTHLY, subscriptionProduct.getBillingCycle());
  }

  @Test
  void isAutoRenew() {
    assertTrue(subscriptionProduct.isAutoRenew());
  }

  @Test
  void getTier() {
    assertEquals(Tier.PREMIUM, subscriptionProduct.getTier());
  }

  @Test
  void isAvailable() {
    assertTrue(subscriptionProduct.isAvailable());
  }

  @Test
  void matches_nameMatches() {
    assertTrue(subscriptionProduct.matches("flix"));
  }

  @Test
  void matches_categoryMatches() {
    assertTrue(subscriptionProduct.matches("stream"));
  }

  @Test
  void matches_billingMatches() {
    assertTrue(subscriptionProduct.matches("monthly"));
  }

  @Test
  void matches_tierMatches() {
    assertTrue(subscriptionProduct.matches("mium"));
  }

  @Test
  void matches_noMatches() {
    assertFalse(subscriptionProduct.matches("Video"));
  }

  @Test
  void testEquals_sameObj() {
    assertTrue(subscriptionProduct.equals(subscriptionProduct));
  }

  @Test
  void testEquals_nullObj() {
    assertFalse(subscriptionProduct.equals(null));
  }

  @Test
  void testEquals_differentClass() {
    String differentClass = "differentClass";
    assertNotEquals(differentClass, subscriptionProduct);
  }

  @Test
  void testEquals_differentAutoRenew() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(false)
        .billingCycle(BillingCycle.MONTHLY)
        .monthlyPrice(24.99)
        .tier(Tier.PREMIUM)
        .build();

    assertNotEquals(subscriptionProduct, other);
  }

  @Test
  void testEquals_sameValues() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.MONTHLY)
        .monthlyPrice(24.99)
        .tier(Tier.PREMIUM)
        .build();

    assertEquals(subscriptionProduct, other);
  }

  @Test
  void testEquals_differentValues() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1013", "Seattle Gym", "Fitness")
        .autoRenew(false)
        .billingCycle(BillingCycle.ANNUAL)
        .monthlyPrice(750.00)
        .tier(Tier.ENTERPRISE)
        .build();

    assertNotEquals(subscriptionProduct, other);
  }

  @Test
  void testEquals_differentBillingCycle() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.QUARTERLY)
        .monthlyPrice(24.99)
        .tier(Tier.PREMIUM)
        .build();

    assertNotEquals(subscriptionProduct, other);

  }

  @Test
  void testEquals_differentMonthlyPrice() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.MONTHLY)
        .monthlyPrice(12.31)
        .tier(Tier.PREMIUM)
        .build();

    assertNotEquals(subscriptionProduct, other);
  }

  @Test
  void testEquals_differentTier() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.MONTHLY)
        .monthlyPrice(24.99)
        .tier(Tier.ENTERPRISE)
        .build();

    assertNotEquals(subscriptionProduct, other);
  }

  @Test
  void testHashCode_sameValuesObj() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1002", "Netflix", "Streaming")
        .autoRenew(true)
        .billingCycle(BillingCycle.MONTHLY)
        .monthlyPrice(24.99)
        .tier(Tier.PREMIUM)
        .build();

    assertEquals(subscriptionProduct.hashCode(), other.hashCode());
  }

  @Test
  void testHashCode_differentValues() {
    SubscriptionProduct other = new SubscriptionProduct.Builder("1013", "Seattle Gym", "Fitness")
        .autoRenew(false)
        .billingCycle(BillingCycle.ANNUAL)
        .monthlyPrice(750.00)
        .tier(Tier.PREMIUM)
        .build();

    assertNotEquals(subscriptionProduct.hashCode(), other.hashCode());

  }

  @Test
  void testToString() {
    String expectedString = "SubscriptionProduct{" +
        "id='" + subscriptionProduct.getID() + '\'' +
        ", name='" + subscriptionProduct.getName() + '\'' +
        ", basePrice=" + subscriptionProduct.getBasePrice() +
        ", category='" + subscriptionProduct.getCategory() + '\'' +
        ", tier=" + subscriptionProduct.getTier() +
        ", billingCycle=" + subscriptionProduct.getBillingCycle() +
        ", monthlyPrice=" + subscriptionProduct.getMonthlyPrice() +
        ", autoRenew=" + subscriptionProduct.isAutoRenew() +
        '}';

    assertEquals(expectedString, subscriptionProduct.toString());
  }
}