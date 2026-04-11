package shoppingCartAndPricing;

import java.util.Map;
import productCatalog.IProduct;

/**
 * This interface represents the Pricing Strategy. Each Pricing Strategy calculates the total
 * price given the entire shopping cart and a count of the number of each product in the cart.
 */
public interface PricingStrategy {

  /**
   * Calculates the total price for all items in shopping cart for a given pricing strategy
   * @param shoppingCart the shopping cart, as generic Iterable type
   * @param quantity the map of number of each item in shopping cart
   * @return the total cost of all items in the shopping cart
   */
  double calculate(Iterable<? extends IProduct> shoppingCart, Map<String, Integer> quantity);
}
