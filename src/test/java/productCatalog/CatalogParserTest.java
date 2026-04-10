package productCatalog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogParserTest {
  private CatalogParser catalogParser;

  private String directory = System.getProperty("user.dir");
  private String fileName;

  @BeforeEach
  void setUp() {
    // create file paths
    fileName = Paths.get(directory, "src", "main", "resources", "catalog_valid.csv").toString();
    catalogParser = new CatalogParser();
  }

  @Test
  void parseCatalog_HappyPath() throws IOException, InvalidCSVFileException {
    List<Map<String, String>> products = catalogParser.parseCatalog(fileName);

    // there are 10 rows of products
    assertEquals(10, products.size());
    assertEquals("P001", products.get(0).get("id"));
  }

  @Test
  void parseCatalog_EmptyCatalog() {
    fileName = Paths.get(directory, "src", "main", "resources", "catalog_empty.csv").toString();
    assertThrows(InvalidCSVFileException.class, () -> catalogParser.parseCatalog(fileName));
  }

  @Test
  void parseCatalog_NullCatalog() {
  }
}