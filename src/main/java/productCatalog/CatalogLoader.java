package productCatalog;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a simple utility class that loads catalog into a map where
 * keys are the id of product, values are the products themselves
 */
public class CatalogLoader {
  private CatalogParser catalogParser;

  /**
   * Constructor, creates a catalog loader given catalog parser
   * @param catalogParser the catalog parser, as custom CatalogParser Data type
   */
  public CatalogLoader(CatalogParser catalogParser) {
    if (catalogParser == null) throw new IllegalArgumentException("catalogParser cannot be null");
    this.catalogParser = catalogParser;
  }

  /**
   * Loads the entire products catalog
   * @param filePath the file path to csv file with all catalog items
   * @return a Map of catalog products where key is product id and value is product
   * @throws IOException if there were issues with File I/O
   * @throws InvalidCSVFileException if the CVS file provided is invalid
   */
  public Map<String, IProduct> load(String filePath) throws IOException, InvalidCSVFileException {
    // create a map to store results and return
    Map<String, IProduct> catalog =  new HashMap<>();

    // get a list of parsed catalog from csv file
    List<Map<String, String>> parsedCatalog = catalogParser.parseCatalog(filePath);

    // go through each and create product
    for(Map<String, String> row : parsedCatalog) {
      IProduct product = ProductFactory.createProduct(row);
      catalog.put(row.get("id"), product);
    }

    return Map.copyOf(catalog);
  }
}
