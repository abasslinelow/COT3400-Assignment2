import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * Contains various specialized file operations for the Optimal Binary Search Tree assignment.
 */
public class FileOperations {

  /**
   * Custom Exception to throw when <T> arrToFile() is not passed a Double[][] or BigDecimal[][]
   * as its generic type.
   */
  public static class IncorrectClassTypeException extends Exception {
    public IncorrectClassTypeException(String e) {
      super(e);
    }
  }

  /**
   * Parses a file and stores any ints to an array of ints.
   *
   * @param fileName The name of the file read the int array from.
   * @return The int array from the file.
   * @throws IOException if file does not exist.
   */
  //
  public static int[] fileToArr(String fileName) throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(fileName));

    int lineCount = 0;
    while (reader.readLine() != null) {
      lineCount++;
    }

    int[] arr = new int[lineCount];
    reader = new BufferedReader(new FileReader(fileName));
    for (int i = 0; i < arr.length; i++) {
      arr[i] = Integer.parseInt(reader.readLine());
    }

    return arr;
  }

  /**
   * Writes an array of ints to a file.
   *
   * @param arr The int array to write to a file.
   * @param fileName The name of the file to write the int array to.
   * @throws IOException if file does not exist.
   */
  //
  public static void arrToFile(int[] arr, String fileName) throws IOException {

    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    for (int i = 0; i < arr.length; i++) {
      writer.write(Integer.toString(arr[i]));
      if (i != arr.length - 1) {
        writer.newLine();
      }
    }
    writer.flush();
    writer.close();
  }

  /**
   * Writes an array of longs to a file.
   *
   * @param arr The long array to write to a file.
   * @param fileName The name of the file to write the long array to.
   * @throws IOException if file does not exist.
   */
  // Writes an array of longs to a file.
  public static void arrToFile(long[] arr, String fileName) throws IOException {

    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    for (int i = 0; i < arr.length; i++) {
      writer.write(Long.toString(arr[i]));
      if (i != arr.length - 1) {
        writer.newLine();
      }
    }
    writer.flush();
    writer.close();
  }

  /**
   * Writes a 2D array of BigDecimals or Doubles to a file. NOTE: This method skips the first row.
   *
   * @param arr 2D array to write to a file.
   * @param fileName Name of the file to write the 2D array to.
   * @param whitespace Amount of whitespace to add between values
   * @param precision Precision of printed values
   * @param <T> BigDecimal or Double
   * @throws IOException if file does not exist.
   * @throws IncorrectClassTypeException if array is not a Double[][] or BigDecimal[][].
   */
  //
  public static <T> void arrToFile(T[][] arr, String fileName, int whitespace, int precision)
      throws IOException, IncorrectClassTypeException {

    if (arr instanceof Double[][] || arr instanceof BigDecimal[][]) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      StringBuilder builder = new StringBuilder();

      for (int row = 1; row < arr.length; row++) {
        for (int col = 0; col < arr[row].length; col++) {
          if (arr[row][col] == null) {
            builder.append(String.format("%" + whitespace + "s", " "));
          } else {
            builder.append(String.format("%" + whitespace + "." + precision + "f", arr[row][col]));
          }
        }
        if (row < arr.length - 1) {
          builder.append("\n");
        }
      }

      writer.write(builder.toString());
      writer.flush();
      writer.close();
    } else {
      throw new IncorrectClassTypeException("Method only accepts Double[][] or BigDecimal[][].");
    }
  }

  /**
   * Writes a 2D array of ints. NOTE: This method skips the first row and the first column.
   *
   * @param arr 2D array to write to a file.
   * @param fileName Name of the file to write the 2D array to.
   * @param whitespace Amount of whitespace to add between values
   * @throws IOException if file does not exist.
   */
  public static void arrToFile(int[][] arr, String fileName, int whitespace)
      throws IOException {

    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    StringBuilder builder = new StringBuilder();

    for (int row = 1; row < arr.length; row++) {
      for (int col = 1; col < arr[row].length; col++) {
        if (arr[row][col] == 0) {
          builder.append(String.format("%" + whitespace + "s", " "));
        } else {
          builder.append(
              String.format("%" + whitespace + "d", arr[row][col]));
        }
      }
      if (row < arr.length - 1) {
        builder.append("\n");
      }
    }

    writer.write(builder.toString());
    writer.flush();
    writer.close();
  }

  /**
   * @param obst An optimal binary search tree.
   * @param values Print keys or their indexes (OBST.KEYS or OBST.INDEXES)
   * @param format Print the tree horizontally or vertically (OBST.HORIZONTAL or OBST.VERTICAL)
   * @param fileName The name of the file to save the binary search tree to.
   * @throws IOException if file does not exist.
   */
  public static void treeToFile(OBST obst, OBST.PrintValues values,
      OBST.PrintFormat format, String fileName) throws IOException {

    PrintStream fileStream = new PrintStream(fileName);
    PrintStream old = System.out;
    System.setOut(fileStream);
    obst.print(values, format);
    System.out.flush();
    System.setOut(old);
  }
}
