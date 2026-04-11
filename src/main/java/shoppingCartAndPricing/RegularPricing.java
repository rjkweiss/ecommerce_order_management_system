package shoppingCartAndPricing;

import java.util.Map;
import productCatalog.IProduct;

/**
 * This class represents regular pricing strategy. Each item is priced at full base price.
 * No discounts are applied to any of the item.
 * The total price takes into account the number of items for each price.
 */
public class RegularPricing implements PricingStrategy {

  @Override
  public double calculate(Iterable<? extends IProduct> shoppingCart, Map<String, Integer> quantity) {
    double totalPrice = 0.0;

    // iterate over the products and calculate the price for product and the number of the item in cart
    for (IProduct product : shoppingCart) {
      Integer numOfProducts = quantity.get(product.getID());
      totalPrice += product.getBasePrice() * numOfProducts;
    }

    return totalPrice;
  }
}
