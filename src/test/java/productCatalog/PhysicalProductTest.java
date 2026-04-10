package productCatalog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhysicalProductTest {
  private PhysicalProduct physicalProduct;

  @BeforeEach
  void setUp() {
    physicalProduct = new PhysicalProduct.Builder("1003", "Electric Kettle",
        24.79, "Kitchen")
        .fragile(false).weight(234.3)
        .warehouseLocation("Warehouse_B")
        .build();
  }

  @Test
  void getWeight() {
    assertEquals(234.3, physicalProduct.getWeight(), 0.01);
  }

  @Test
  void getWarehouseLocation() {
    assertEquals("Warehouse_B", physicalProduct.getWarehouseLocation());
  }

  @Test
  void isFragile() {
    assertFalse(physicalProduct.isFragile());
  }

  @Test
  void isAvailable() {
    assertTrue(physicalProduct.isAvailable());
  }

  @Test
  void matches_name() {
    assertTrue(physicalProduct.matches("kettle"));
  }

  @Test
  void matches_category() {
    assertTrue(physicalProduct.matches("kitchen"));
  }

  @Test
  void matches_warehouse() {
    assertTrue(physicalProduct.matches("warehouse"));
  }

  @Test
  void testEquals_self() {
    assertEquals(physicalProduct, physicalProduct);
  }

  @Test
  void testEquals_null() {
    assertNotEquals(null, physicalProduct);
  }

  @Test
  void testEquals_differentDataTypes() {
    String differentDataTypes = "differentDataTypes";
    assertNotEquals(differentDataTypes, physicalProduct);
  }

  @Test
  void testEquals_differentWeight() {
    PhysicalProduct other = new PhysicalProduct.Builder("1003", "Electric Kettle",
        24.79, "Kitchen")
        .fragile(false).weight(128.1)
        .warehouseLocation("Warehouse_B")
        .build();

    assertNotEquals(other, physicalProduct);
  }

  @Test
  void testEquals_differentWarehouseLocation() {
    PhysicalProduct other = new PhysicalProduct.Builder("1003", "Electric Kettle",
        24.79, "Kitchen")
        .fragile(false).weight(234.3)
        .warehouseLocation("Warehouse_A")
        .build();

    assertNotEquals(other, physicalProduct);
  }

  @Test
  void testEquals_differentFragile() {
    PhysicalProduct other = new PhysicalProduct.Builder("1003", "Electric Kettle",
        24.79, "Kitchen")
        .fragile(true).weight(234.3)
        .warehouseLocation("Warehouse_B")
        .build();

    assertNotEquals(other, physicalProduct);
  }

  @Test
  void testEquals_sameObject() {
    PhysicalProduct other = new PhysicalProduct.Builder("1003", "Electric Kettle",
        24.79, "Kitchen")
        .fragile(false).weight(234.3)
        .warehouseLocation("Warehouse_B")
        .build();

    assertEquals(physicalProduct, other);
  }

  @Test
  void testEquals_differentValues() {
    PhysicalProduct other = new PhysicalProduct.Builder("1004", "Kitchen Towels",
        39.99, "Paper Products")
        .fragile(true).weight(50.23)
        .warehouseLocation("Warehouse_A")
        .build();

    assertNotEquals(other, physicalProduct);
  }

  @Test
  void testHashCode_sameObject() {
    PhysicalProduct other = new PhysicalProduct.Builder("1003", "Electric Kettle",
        24.79, "Kitchen")
        .fragile(false).weight(234.3)
        .warehouseLocation("Warehouse_B")
        .build();

    assertEquals(physicalProduct.hashCode(), other.hashCode());
  }

  @Test
  void testHashCode_differentData() {
    PhysicalProduct other = new PhysicalProduct.Builder("1004", "Kitchen Towels",
        39.99, "Paper Products")
        .fragile(true).weight(50.23)
        .warehouseLocation("Warehouse_A")
        .build();

    assertNotEquals(other.hashCode(), physicalProduct.hashCode());
  }

  @Test
  void testToString() {
    String expectedStr = "PhysicalProduct{" +
        "id='" + physicalProduct.getID() + '\'' +
        ", name='" + physicalProduct.getName() + '\'' +
        ", basePrice=" + physicalProduct.getBasePrice() +
        ", category='" + physicalProduct.getCategory() + '\'' +
        ", weight=" + physicalProduct.getWeight() +
        ", fragile=" + physicalProduct.isFragile() +
        ", warehouseLocation='" + physicalProduct.getWarehouseLocation() + '\'' +
        '}';

    assertEquals(expectedStr, physicalProduct.toString());
  }
}