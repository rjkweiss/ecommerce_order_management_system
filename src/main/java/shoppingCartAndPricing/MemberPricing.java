package shoppingCartAndPricing;

import java.util.Map;
import productCatalog.IProduct;

/**
 * This class represents Member Pricing Strategy. Members get 10% discount on all items.
 * The total price takes into account the number of items for each price.
 * The total price takes into account the number of items for each price.
 */
public class MemberPricing implements PricingStrategy {

  // constant for member discount rate
  private static final double MEMBER_DISCOUNT_RATE = 0.1;

  @Override
  public double calculate(Iterable<? extends IProduct> shoppingCart, Map<String, Integer> quantity) {
    double totalPrice = 0.0;

    // traverse product and apply discount to each item
    for (IProduct product : shoppingCart) {
      Integer numOfProducts = quantity.get(product.getID());
      totalPrice += (product.getBasePrice() * (1 - MEMBER_DISCOUNT_RATE)) * numOfProducts;
    }

    return totalPrice;
  }
}
