package shoppingCartAndPricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import productCatalog.IProduct;

/**
 * This class represents a shopping cart in the ShopSmart ecommerce order management system.
 * A shopping cart supports adding items in multiple quantities, removing items from cart, retrieving
 * item by id and calculating the total cost of the cart.
 * @param <T> generic placeholder for product types supported
 */
public class ShoppingCart<T extends IProduct> implements Iterable<T> {
  private LinkedHashMap<String, T> products;
  private Map<String, Integer> productCount;

  /**
   * constructor, creates an empty shopping cart with no products in it
   */
  public ShoppingCart() {
    this.products = new LinkedHashMap<>();
    this.productCount = new HashMap<>();
  }

  /**
   * Adds product to the shopping cart
   * @param product the product to add to cart, as generic T data type
   */
  public void addProduct(T product) {
    // get id
    String productId = product.getID();
    products.put(productId, product);

    // increment count for product count
    productCount.put(productId, productCount.getOrDefault(productId, 0) + 1);
  }

  /**
   * Removes product from shopping cart
   * @param id the id of product to remove, as String
   * @throws NoSuchElementException if no product with given id in shopping cart
   */
  public void removeProduct(String id) throws NoSuchElementException {
    // throw error if not found
    if (!products.containsKey(id)) {
      throw new NoSuchElementException("Product with ID " + id + " not found");
    }

    // decrement count if greater than 0
    int newCount = productCount.get(id) - 1;

    if (newCount == 0) {
      products.remove(id);
      productCount.remove(id);
    } else {
      productCount.put(id, newCount);
    }
  }

  /**
   * Retrieves product in shopping cart using product id
   * @param id the id of product to get, by String
   * @return the product with given id, as generic T data type
   * @throws NoSuchElementException if no product with give id in shopping cart
   */
  public T getProductByID(String id) throws NoSuchElementException {
    if (!products.containsKey(id))
      throw new NoSuchElementException("Product with ID " + id + " not found");

    return products.get(id);
  }

  /**
   * Calculates the total price of all items currently in shopping cart
   * @param strategy the pricing strategy to apply, as custom PricingStrategy
   * @return the total price of cart, as double
   */
  double getTotalPrice(PricingStrategy strategy) {
    return strategy.calculate(this, productCount);
  }

  @Override
  public Iterator<T> iterator() {
    return new ProductIterator<>(products);
  }

  // --------------------------------- shopping cart iterator ------------------------------------//

  /**
   * This inner class represents a product iterator that allows us to traverse products in the shopping
   * cart. It is private since it only get used by the shopping cart.
   * @param <T> generic type placeholder
   */
  private static class ProductIterator<T extends IProduct> implements Iterator<T> {
    private List<T> products = new ArrayList<>();
    private int currentIndex;

    /**
     * Constructor, creates iterator with products in the shopping cart
     * @param products the key-value collection of shopping cart products
     */
    public ProductIterator(LinkedHashMap<String, T> products) {
      // filter the shopping cart
      this.products.addAll(products.values());
      this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
      return this.currentIndex < this.products.size();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!this.hasNext()) {
        throw new NoSuchElementException();
      }
      return this.products.get(this.currentIndex++);
    }
  }
}
