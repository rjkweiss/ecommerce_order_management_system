package shoppingCartAndPricing;

import java.util.Map;
import productCatalog.IProduct;

/**
 * This class represents Bulk Item pricing strategy. Any item with 5 or more items
 * gets 25% discount.
 * The total price takes into account the number of items for each price.
 */
public class BulkPricing implements PricingStrategy {

  // constant for bulk discount rate
  private static final double BULK_ITEM_DISCOUNT = 0.25;

  @Override
  public double calculate(Iterable<? extends IProduct> shoppingCart, Map<String, Integer> quantity) {
    double totalPrice = 0.0;

    // iterate through products and apply discount if applicable
    for (IProduct product : shoppingCart) {
      Integer numOfProduct = quantity.get(product.getID());

      // only apply discount for items with >= 5 items
      if (numOfProduct >= 5)
        totalPrice += (product.getBasePrice() * (1 - BULK_ITEM_DISCOUNT)) * numOfProduct;
      else
        totalPrice += (product.getBasePrice() * numOfProduct);
    }

    return totalPrice;
  }
}
