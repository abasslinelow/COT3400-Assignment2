/**
 * @param <T>
 */
public class ProbabilityGenerator<T> {

  /**
   * Probabilities for all real keys.
   */
  T[] p;

  /**
   * Probabilities for all dummy keys.
   */
  T[] q;

  /**
   * Probabilities for all real keys and all dummy keys.
   */
  T[] pq;

  /**
   * Splits the combined probabilities into separate arrays: one for real keys, one for dummy keys.
   */
  void splitPQ() {
    for (int i = 0; i < p.length; i++) {
      p[i] = pq[i];
    }
    for (int i = 0; i < q.length; i++) {
      q[i] = pq[i + p.length];
    }
  }
}
