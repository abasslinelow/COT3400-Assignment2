/**
 * Extends OBST to perform operations on double values. Note that generics do not allow for
 * double primitives, so we box a primitive in a Double object, then unbox it before performing
 * computationally heavy operations.
 */
public class OBSTDouble extends OBST<Double> {

  /**
   * @param keys Key set to use to compute and construct the optimal binary search tree.
   * @param p Probabilities of searching for each key in the key set.
   * @param q Probabilities of searching for keys that don't exist in the key set.
   * @param n Number of keys.
   */
  public OBSTDouble(int[] keys, Double[] p, Double[] q, int n) {
    this.keys = keys;
    this.p = p;
    this.q = q;
    this.n = n;

    root = new int[n + 1][n + 1];
  }

  /**
   * Calculates the optimal binary search tree based on the probabilities of searching for each key.
   * We do this by constructing three matrices: the e matrix, which holds the expected cost of
   * each key range; the w matrix, which holds the probabilities of each key range; and the root
   * matrix, which holds the indexes of the optimal root for each key range.
   *
   * In this algorithm, i and j correspond to the lower and upper bound of a range, e.g.,
   * i=2, j=8 corresponds to keys 2, 3, ..., 8.
   *
   * We fill the w matrix starting from the bottom - that is, a single key - and calculating the
   * weighted probability by adding the probability of the previous key in the range (i.e. [i][j-1])
   * with the probability of the jth key and jth dummy key.
   *
   * We fill the e matrix by comparing each possible i and j value in the range that has been
   * previously calculated in the e matrix and finding the lowest. i.e., for e[2][4] in a key set
   * with 5 keys, we would add [2][3] and [5][4]; then [2][2] and [4][4]; then [2][1] and [3][4].
   * We pick the lowest value, then add the probability found in the parallel w[2][4] element.
   *
   * We fill the root matrix with the indexes that give us the minimum cost, as explained in the
   * comments in the code below.
   */
  public void optimalBST() {

    // For more efficient computation, we unbox the Double objects before performing the
    // computationally heavy algorithm.
    double[] p = unboxDoubleArray(this.p);
    double[] q = unboxDoubleArray(this.q);

    double[][] e = new double[n + 2][n + 1];
    double[][] w = new double[n + 2][n + 1];

    // Fill the bottom of two 2D matrices with the dummy key values. e will hold the expected cost
    // of searching through a range of keys, and w will hold the probabilities of searching for a
    // key within a range of keys.
    for (int i = 1; i <= n + 1; i++) {
      e[i][i - 1] = q[i - 1];
      w[i][i - 1] = q[i - 1];
    }

    // When l=1, we iterate e[i, i] and w[i, i] for [1..n].
    // When l=2, we iterate e[i, i+1] and w[i, i+1] for [1..n-1]
    // etc
    for (int l = 1; l <= n; l++) {
      for (int i = 1; i <= (n - l + 1); i++) {
        int j = i + l - 1;

        // Set the current expected cost to a max value for later comparison.
        e[i][j] = Double.MAX_VALUE;

        // Calculates the weighted possibility of searching for a key in range [i..j].
        w[i][j] = w[i][j - 1] + p[j - 1] + q[j];

        // Tries each candidate index r to determine which key to use as the root.
        for (int r = i; r <= j; r++) {

          // Add the probability of the candidate index shortened by one on the upper bound to the
          // probability of the candidate index shortened by one on the lower bound and add
          // the weighted probability.
          double t = e[i][r - 1] + e[r + 1][j] + w[i][j];

          // If this results in a lower cost than the currently-stored key, store its index to the.
          // root. This is why we set e[i][j] to a maximum value - so the first comparison will
          // always be true.
          if (t < e[i][j]) {
            e[i][j] = t;
            root[i][j] = r;
          }
        }
      }
    }

    // Now that the computation is done, we can convert the matrices to Doubles to store in the
    // parent class.
    this.e = boxDoubleMatrix(e);
    this.w = boxDoubleMatrix(w);
  }

  /**
   * Unboxes a Double array to a double array.
   *
   * @param arr Double array to unbox.
   * @return Unboxed double array.
   */
  public double[] unboxDoubleArray(Double[] arr) {
    double[] newArr = new double[arr.length];
    for (int i = 0; i < arr.length; i++) {
      newArr[i] = arr[i];
    }
    return newArr;
  }

  /**
   *Boxes a double 2D array to a Double 2D array.
   *
   * @param arr double 2D array to box.
   * @return Boxed Double 2D array.
   */
  public Double[][] boxDoubleMatrix(double[][] arr) {
    Double[][] newArr = new Double[arr.length][arr[0].length];
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        newArr[i][j] = arr[i][j];
      }
    }
    return newArr;
  }
}
