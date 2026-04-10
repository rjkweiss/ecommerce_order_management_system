package productCatalog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DigitalProductTest {
  private DigitalProduct digitalProduct;

  @BeforeEach
  void setUp() {
    // create a digital product with all fields (for testing purposes)
    digitalProduct = new DigitalProduct.Builder("1001", "Budgeting workbook",
        "finance", 29.99)
        .fileSize(5.3)
        .licenseType(LicenseType.MULTI_DEVICE)
        .downloadUrl("https://www.someurl.io")
        .build();
  }

  @Test
  void testSetup_onlyRequiredFields() {
    DigitalProduct other = new DigitalProduct.Builder("1", "Hunger Games", "video", 10.00).build();
    assertNotNull(other);

    // test for defaults
    assertEquals(0, other.getFileSize(), 0.01);
    assertEquals("", other.getDownloadUrl());
    assertEquals(LicenseType.SINGLE_USE, other.getLicenseType());
  }

  @Test
  void testSetup_invalidRequiredFields() {
    assertThrows(IllegalArgumentException.class, () -> {
      new DigitalProduct.Builder("", "Hunger Games", "video", 10.00).build();
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new DigitalProduct.Builder("1", null, "video", 10.00).build();
    });
  }

  @Test
  void testSetup_partialOptionalFields() {
    DigitalProduct other = new DigitalProduct.Builder("1001", "Budgeting workbook",
        "finance", 29.99)
        .fileSize(5.3)
        .build();

    // the others are set as defaults
    assertEquals("", other.getDownloadUrl());
    assertEquals(LicenseType.SINGLE_USE, other.getLicenseType());
  }

  @Test
  void getFileSize() {
    assertEquals(digitalProduct.getFileSize(), 5.3, 0.01);
  }

  @Test
  void getDownloadUrl() {
    assertEquals(digitalProduct.getDownloadUrl(), "https://www.someurl.io");
  }

  @Test
  void getLicenseType() {
    assertEquals(digitalProduct.getLicenseType(), LicenseType.MULTI_DEVICE);
  }

  @Test
  void isAvailable() {
    assertTrue(digitalProduct.isAvailable());
  }

  @Test
  void matches_matchesCategory() {
    assertTrue(digitalProduct.matches("finance"));
  }

  @Test
  void matches_matchesLicenseType() {
    assertTrue(digitalProduct.matches("multi"));
  }

  @Test
  void matches_matchesDownloadUrl() {
    assertTrue(digitalProduct.matches("url"));
  }

  @Test
  void matches_matchesName() {
    assertTrue(digitalProduct.matches("book"));
  }

  @Test
  void matches_noMatch() {
    assertFalse(digitalProduct.matches("Money"));
  }

  @Test
  void testEquals_sameObj() {
    assertTrue(digitalProduct.equals(digitalProduct));
  }

  @Test
  void testEquals_null() {
    assertFalse(digitalProduct.equals(null));
  }

  @Test
  void testEquals_differentDataType() {
    String other = "other";
    assertFalse(digitalProduct.equals(other));
  }

  @Test
  void testEquals_differentLicenseType() {
    DigitalProduct other = new DigitalProduct.Builder("1001", "Budgeting workbook",
        "finance", 29.99)
        .fileSize(5.3)
        .licenseType(LicenseType.ENTERPRISE)
        .downloadUrl("https://www.someurl.io")
        .build();

    assertFalse(digitalProduct.equals(other));
  }

  @Test
  void testEquals_differentDownloadUrl() {
    DigitalProduct other = new DigitalProduct.Builder("1001", "Budgeting workbook",
        "finance", 29.99)
        .fileSize(5.3)
        .licenseType(LicenseType.MULTI_DEVICE)
        .downloadUrl("localhost.com//www.otherUrl.test")
        .build();

    assertFalse(digitalProduct.equals(other));
  }

  @Test
  void testEquals_differentFileSize() {
    DigitalProduct other = new DigitalProduct.Builder("1001", "Budgeting workbook",
        "finance", 29.99)
        .fileSize(3.1)
        .licenseType(LicenseType.MULTI_DEVICE)
        .downloadUrl("https://www.someurl.io")
        .build();

    assertFalse(digitalProduct.equals(other));
  }

  @Test
  void testEquals_differentObjectSameValues() {
    DigitalProduct other = new DigitalProduct.Builder("1001", "Budgeting workbook",
        "finance", 29.99)
        .fileSize(5.3)
        .licenseType(LicenseType.MULTI_DEVICE)
        .downloadUrl("https://www.someurl.io")
        .build();

    assertTrue(digitalProduct.equals(other));
  }

  @Test
  void testEquals_differentValues() {
    DigitalProduct other = new DigitalProduct.Builder("1002", "Chronicles of Narnia",
        "Audio Book", 17.50)
        .fileSize(388.5)
        .licenseType(LicenseType.SINGLE_USE)
        .downloadUrl("https://www.audible.com")
        .build();

    assertFalse(digitalProduct.equals(other));
  }

  @Test
  void testHashCode_sameObject() {
    assertEquals(digitalProduct.hashCode(), digitalProduct.hashCode());
  }

  @Test
  void testHashCode_differentObjects() {
    DigitalProduct other = new DigitalProduct.Builder("1002", "Chronicles of Narnia",
        "Audio Book", 17.50)
        .fileSize(388.5)
        .licenseType(LicenseType.SINGLE_USE)
        .downloadUrl("https://www.audible.com")
        .build();
    assertNotEquals(digitalProduct.hashCode(), other.hashCode());
  }

  @Test
  void testToString() {
    String expectedString = "DigitalProduct{" +
        "id='" + digitalProduct.getID() + '\'' +
        ", name='" + digitalProduct.getName() + '\'' +
        ", basePrice=" + digitalProduct.getBasePrice() +
        ", category='" + digitalProduct.getCategory() + '\'' +
        ", fileSize=" + digitalProduct.getFileSize() +
        ", downloadUrl='" + digitalProduct.getDownloadUrl() + '\'' +
        ", licenseType=" + digitalProduct.getLicenseType() +
        '}';

    assertEquals(expectedString, digitalProduct.toString());
  }
}