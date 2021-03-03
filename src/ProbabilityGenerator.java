/**
 * Generates random numbers that simulate search probabilities for keys in a set, as well as
 * search probabilities for keys that do not exist in the set. Accepts either a BigDecimal or Double
 * as a generic, which allows it to work with either data type. See ProbabilityGeneratorBigDecimal
 * and ProbabilityGeneratorDouble for each implementation.
 *
 * @param <T> BigDecimal or Double
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
   * Splits the pq array into the p array and the q array. That is, it splits the array containing
   * all probabilities into two separate arrays: one for real keys, one for dummy keys.
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
