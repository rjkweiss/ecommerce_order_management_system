package shoppingCartAndPricing;

import java.util.EnumMap;
import java.util.Map;
import productCatalog.IProduct;
import productCatalog.ProductType;

/**
 * This class represents Holiday Sale Pricing Strategy for products. Holiday pricing only applies
 * to Physical and Digital products.
 * The total price takes into account the number of items for each price.
 */
public class HolidaySale implements PricingStrategy {

  // enum map that stores discount rate for each product type
  private static final EnumMap<ProductType, Double> HOLIDAY_DISCOUNT = new EnumMap<>(
      Map.of(ProductType.SUBSCRIPTION, 0.0, ProductType.DIGITAL, 0.3, ProductType.PHYSICAL, 0.2));

  @Override
  public double calculate(Iterable<? extends IProduct> shoppingCart, Map<String, Integer> quantity) {
    double totalPrice = 0.0;

    // iterate through products and apply holiday discount appropriately
    for (IProduct product : shoppingCart) {
      Integer numOfProduct = quantity.get(product.getID());
      double final_rate = 1 - HOLIDAY_DISCOUNT.get(product.getProductType());
      totalPrice += (product.getBasePrice() * final_rate) * numOfProduct;
    }

    return totalPrice;
  }
}
