package productCatalog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogLoaderTest {
  private CatalogLoader catalogLoader;

  @BeforeEach
  void setUp() {
    catalogLoader = new CatalogLoader(new CatalogParser());
  }

  @Test
  void test_invalidCSV() {
    assertThrows(IllegalArgumentException.class, () -> {
      new CatalogLoader(null);
    });
  }

  @Test
  void load() throws IOException, InvalidCSVFileException {
    String filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "catalog_valid.csv").toString();
    Map<String, IProduct> catalog = catalogLoader.load(filePath);

    // tests
    assertNotNull(catalog);
    assertEquals(10, catalog.size());
  }
}