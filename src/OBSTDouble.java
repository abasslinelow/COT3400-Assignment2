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
   */
  public void optimalBST() {

    // For more efficient computation, we unbox the Double objects before performing the
    // computationally heavy algorithm.
    double[] p = unboxDoubleArray(this.p);
    double[] q = unboxDoubleArray(this.q);

    double[][] e = new double[n + 2][n + 1];
    double[][] w = new double[n + 2][n + 1];

    // Fill the bottom of two 2D matrices with the dummy key values.
    for (int i = 1; i <= n + 1; i++) {
      e[i][i - 1] = q[i - 1];
      w[i][i - 1] = q[i - 1];
    }

    for (int l = 1; l <= n; l++) {
      for (int i = 1; i <= (n - l + 1); i++) {
        int j = i + l - 1;
        e[i][j] = Double.MAX_VALUE;
        w[i][j] = w[i][j - 1] + p[j - 1] + q[j];
        for (int r = i; r <= j; r++) {
          double t = e[i][r - 1] + e[r + 1][j] + w[i][j];
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
