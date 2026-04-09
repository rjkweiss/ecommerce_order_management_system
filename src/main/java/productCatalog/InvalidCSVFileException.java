package productCatalog;

/**
 * Custom Exception that is thrown when we encounter an invalid
 * CSV file
 */
public class InvalidCSVFileException extends Exception {

  /**
   * Constructor, creates exception with given message
   * @param message the message for exception, as String
   */
  public InvalidCSVFileException(String message) {
    super(message);
  }
}
