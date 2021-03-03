import java.math.BigDecimal;

/**
 * Extends OBST to perform operations on BigDecimal values.
 */
public class OBSTBigDecimal extends OBST<BigDecimal> {

  /**
   * @param keys Key set to use to compute and construct the optimal binary search tree.
   * @param p Probabilities of searching for each key in the key set.
   * @param q Probabilities of searching for keys that don't exist in the key set.
   * @param n Number of keys.
   */
  public OBSTBigDecimal(int[] keys, BigDecimal[] p, BigDecimal[] q, int n) {
    this.keys = keys;
    this.p = p;
    this.q = q;
    this.n = n;

    e = new BigDecimal[n + 2][n + 1];
    w = new BigDecimal[n + 2][n + 1];
    root = new int[n + 1][n + 1];
  }

  /**
   * Calculates the optimal binary search tree based on the probabilities of searching for each key.
   */
  public void optimalBST() {

    // Fill the bottom of two 2D matrices with the dummy key values.
    for (int i = 1; i <= n + 1; i++) {
      e[i][i - 1] = q[i - 1];
      w[i][i - 1] = q[i - 1];
    }


    for (int l = 1; l <= n; l++) {
      for (int i = 1; i <= (n - l + 1); i++) {
        int j = i + l - 1;
        e[i][j] = BigDecimal.valueOf(Double.MAX_VALUE);
        w[i][j] = w[i][j - 1].add(p[j - 1]).add(q[j]);
        for (int r = i; r <= j; r++) {
          BigDecimal t = e[i][r - 1].add(e[r + 1][j]).add(w[i][j]);
          if (t.compareTo(e[i][j]) < 0) {
            e[i][j] = t;
            root[i][j] = r;
          }
        }
      }
    }
  }
}
