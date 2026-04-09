package productCatalog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a catalog parser. It takes a CSV file with all product data,
 * parses it into a list of maps where each element of the list is a product row and the map
 * is a header - product value mapping for each product
 */
public class CatalogParser {

  /**
   * This method parses the catalog into a List of Maps with individual product information
   * @param catalogFilePath the path to the csv file, as String
   * @return a List of Maps representing information for each product
   * @throws IOException if there was a problem reading the CSV file
   * @throws InvalidCSVFileException if the CSV file is invalid
   */
  public List<Map<String, String>> parseCatalog(String catalogFilePath) throws IOException, InvalidCSVFileException {

    try (BufferedReader reader = new BufferedReader(new FileReader(catalogFilePath))) {
      // create list
      List<Map<String, String>> catalog = new ArrayList<>();


      // get the header
      String headerRow = reader.readLine();
      if (headerRow == null || headerRow.isBlank()) {
        throw new InvalidCSVFileException("CSV file is empty");
      }

      // parse header
      List<String> headers = parseRow(headerRow);
      if (headers.isEmpty()) {
        throw new InvalidCSVFileException("Cannot parse CSV file. Invalid file format");
      }

      // go through line by line
      String line;
      while ((line = reader.readLine()) != null) {
        // parse each line
        List<String> row = parseRow(line);
        if (row.isEmpty()) continue;

        // create a map to store each customer data
        Map<String, String> attributes = new HashMap<>();
        for(int i = 0; i < headers.size(); i++) {
          String value = row.get(i);
          String key = headers.get(i);

          attributes.put(key, value);
        }

        // add a list of immutable map for each product row
        catalog.add(Map.copyOf(attributes));
      }

      // return immutable list
      return List.copyOf(catalog);
    }
  }

  // ---------------------------------- Helper method ------------------------------------------- //

  /**
   * Parses a single line of a csv file -- splits each line into a list of strings
   * @param line the line to parse, as String
   * @return a list of strings representing each field in CSV, as List of Strings
   */
  private List<String> parseRow(String line) {
    return List.of(line.split("\\s*,\\s*"));
  }
}
