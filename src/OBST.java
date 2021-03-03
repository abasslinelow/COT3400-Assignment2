import java.io.IOException;

/**
 * Calculates and constructs an optimal binary serarch tree based off of search probabilities.
 * Accepts either a BigDecimal or Double as a generic, which allows it to work with either data
 * type. See OBSTBigDecimal and OBSTDouble for each implementation.
 *
 * @param <T> BigDecimal or Double
 */
public abstract class OBST<T> {

  /**
   * Used with print() function to specify whether to print a horizontal or vertical tree.
   */
  public enum PrintFormat { HORIZONTAL, VERTICAL }

  /**
   * Used with print() function to specify whether to print the keys or their original indexes.
   */
  public enum PrintValues { KEYS, INDEXES }

  /**
   * Number of keys.
   */
  int n;

  /**
   * Probabilities for all real keys.
   */
  T[] p;

  /**
   * Probabilities for all dummy keys.
   */
  T[] q;

  /**
   * Keys to be processed into an optimal binary search tree.
   */
  int[] keys;

  /**
   * Expected cost of searching for keys within a given range.
   */
  T[][] e;

  /**
   * @return 2D array representing the expected cost of searching within a range of keys.
   */
  public T[][] getE() {return e;}

  /**
   * Probability of searching for any key in a given range.
   */
  T[][] w;

  /**
   * @return 2D array representing the probability of searching for a key in a range of keys.
   */
  public T[][] getW() {return w;}

  /**
   * Indexes of optimal roots for each key range.
   */
  int[][] root;

  /**
   * @return 2D array representing the indexes of optimal roots for each key range.
   */
  public int[][] getRoot() {return root;}

  /**
   * An optimized binary search tree containing the keys.
   */
  BST bstKeys = new BST();

  /**
   * An optimized binary search tree containing the original indexes of the keys.
   */
  BST bstIndexes = new BST();

  /**
   * Calculates the optimal binary search tree.
   */
  public abstract void optimalBST();

  /**
   * Constructs the optimal binary search tree based on the calculations of optimalBST().
   * It does so by looking at the value in root[i][j] - that is, the range of keys from i to j -
   * and using the index stored in this element to make the root of the current sub-tree.
   *
   * Let us take a tree representing 5 keys. The root of the entire tree will always be root[1][n],
   * because this represents the range of keys 1-n, which is the entire key set. We then take the
   * index in this element and recursively work down one side, then the other. For instance, if
   * root[1][n] = 4, then the 3rd key will be the root of the tree.
   *
   * Since this leaves keys 1, 2, and 3 on the left, we need to find the optimal root of all
   * possible roots for keys 1 through 3. This is found in root[1][3]. If this is 2, then we know
   * that 1 is the left child and 2 is the right child.
   *
   * Going to the right side of of 4 is simple, as there is only one key left: 5. This leaves us
   * with a tree with the following structure:
   *
   *         4
   *        / \
   *       2   5
   *      / \
   *     1   3
   *
   * Using this conceptual framework, we can create the algorithm in this function.
   *
   * @param i The lower bound of the range.
   * @param j The upper bound of the range.
   * @param prevRoot
   */
  public void constructOBST(int i, int j, int prevRoot) {
    int currentRoot = root[i][j];
    int rootValue = keys[currentRoot - 1];

    bstIndexes.add(currentRoot);
    bstKeys.add(rootValue);

    // If the current root is the optimal root for all keys, no recursion has been performed,
    // the previous root does not exist - so we make the current root act as the previous root.
    if (currentRoot == root[1][root.length - 1]) {
      prevRoot = currentRoot;
    }

    // If the current root is greater than the lowest key in the range being evaluated, then we
    // still have values in the j row we need to evaluate for the left child, so work our
    // way downwards through the matrix.
    if (currentRoot > i) {

      // Since we're going down, we decrease j by one and evaluate that node.
      currentRoot = root[i][prevRoot - 1];
      constructOBST(i, prevRoot - 1, currentRoot);
    }

    // If the current root is less than the highest key in the range being evaluated, then we
    // still have values in the i column we need to evaluate for the right child, so work our way
    // to the right through the matrix.
    if (currentRoot < j && prevRoot + 1 < root.length && root[prevRoot + 1][j] != 0) {

      // Since we are going right, we increase i by one and evaluate that node.
      currentRoot = root[prevRoot + 1][j];
      constructOBST(prevRoot + 1, j, currentRoot);
    }
  }

  /**
   * Prints the binary search tree in the format specified by the passed arguments.
   *
   * @param values KEYS will print the keys; INDEXES will print the original indexes of the keys.
   * @param format HORIZONTAL will print the tree horizontally; VERTICAL will print vertically.
   */
  public void print(PrintValues values, PrintFormat format) {
    BST tree;

    if (values == PrintValues.KEYS) {
      tree = bstKeys;
    } else if (values == PrintValues.INDEXES) {
      tree = bstIndexes;
    } else {
      tree = null;
      System.out.println("PrintValues must be KEYS or INDEXES.");
    }
    if (tree != null) {
      if (format == PrintFormat.HORIZONTAL) {
        tree.printHorizontal();
      } else if (format == PrintFormat.VERTICAL) {
        tree.printVertical();
      } else {
        System.out.println("PrintFormat must be HORIZONTAL or VERTICAL.");
      }
    }
  }
}