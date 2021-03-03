import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Generates random numbers that simulate search probabilities for keys in a set, as well as
 * search probabilities for dummy keys, i.e. keys that do not exist in the set.
 */
public class ProbabilityGeneratorBigDecimal extends ProbabilityGenerator<BigDecimal> {

  /**
   * Initializes the probability generator.
   *
   * @param n Number of keys in the key set.
   */
  public ProbabilityGeneratorBigDecimal(int n) {
    p = new BigDecimal[n];
    q = new BigDecimal[n + 1];
    pq = generateRandom((n * 2) + 1);

    // We need to generate probabilities for all keys AND dummy keys, and there are n+1 dummy keys.
    // real keys + dummy keys = n + n + 1 = 2n + 1.
    generateProbabilities();
  }

  /**
   * Generates n random values.
   *
   * @param n Total combined number of keys and dummy keys.
   * @return Array of probabilities.
   */
  private BigDecimal[] generateRandom(int n) {
    Random rand = new Random();
    BigDecimal[] arr = new BigDecimal[n];

    // For each element, generate a random integer. Use Integer.MAX_VALUE for maximum variability.
    for (int i = 0; i < n; i++) {
      arr[i] = BigDecimal.valueOf(rand.nextInt(Integer.MAX_VALUE));
    }

    return arr;
  }

  /**
   * Converts an array of values to their corresponding probabilities.
   * Stores results in arrays representing the probability of searching for a real key in the
   * key set, as well probabilities for searching for a dummy key.
   */
  public void generateProbabilities() {
    BigDecimal sum = new BigDecimal(0);

    // Sum all probabilities in the array.
    for (BigDecimal v : pq) {
      sum = sum.add(v);
    }

    // Divide each element by the sum.
    for (int i = 0; i < pq.length; i++) {
      pq[i] = pq[i].divide(sum, 10, RoundingMode.HALF_UP);
    }

    // Reset the sum and re-sum the probabilities. The sum of all probabilities should be 1, but
    // floating point arithmetic is inherently imprecise. This new sum will be used to offset the
    // largest value in the probability array so the sum of all probabilities = 1.
    sum = new BigDecimal(0);
    for (BigDecimal v : pq) {
      sum = sum.add(v);
    }

    addSumOffset(sum);

    // Finally, we split the results into two arrays: one for real keys, one for dummy keys.
    splitPQ();
  }

  /**
   * Adds an offset to the largest value in the probability array so the sum of all probabilities
   * is equal to 1. This is necessary due to the imprecise nature of floating point arithmetic.
   * NOTE: This does not succeed every time, but it succeeds most of the time; and when it does
   * not succeed, it narrows the gap to a trivial amount.
   *
   * @param sum The sum of all probabilities in the pq array.
   */
  private void addSumOffset(BigDecimal sum) {
    BigDecimal modVal = new BigDecimal(0);
    int modIndex = -1;

    // Find the largest element in the array and store its index.
    for (int i = 0; i < pq.length; i++) {
      if (pq[i].compareTo(modVal) > 0) {
        modVal = pq[i];
        modIndex = i;
      }
    }

    // Use the stored index to add the offset to the value.
    if (modIndex != -1) {
      pq[modIndex] = pq[modIndex].add(BigDecimal.valueOf(1).subtract(sum));
    }
  }
}
