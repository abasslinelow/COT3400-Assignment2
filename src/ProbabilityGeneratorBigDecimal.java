import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 */
public class ProbabilityGeneratorBigDecimal extends ProbabilityGenerator<BigDecimal> {

  /**
   * @param n
   */
  public ProbabilityGeneratorBigDecimal(int n) {
    p = new BigDecimal[n];
    q = new BigDecimal[n + 1];
    pq = generateRandom((n * 2) + 1);

    generateProbabilities();
  }

  /**
   * @param n
   * @return
   */
  private BigDecimal[] generateRandom(int n) {
    Random rand = new Random();
    BigDecimal[] arr = new BigDecimal[n];

    for (int i = 0; i < n; i++) {
      arr[i] = BigDecimal.valueOf(rand.nextInt(Integer.MAX_VALUE));
    }

    return arr;
  }

  /**
   *
   */
  public void generateProbabilities() {
    BigDecimal sum = new BigDecimal(0);
    for (BigDecimal v : pq) {
      sum = sum.add(v);
    }

    for (int i = 0; i < pq.length; i++) {
      pq[i] = pq[i].divide(sum, 10, RoundingMode.HALF_UP);
    }

    sum = new BigDecimal(0);
    for (BigDecimal v : pq) {
      sum = sum.add(v);
    }

    addSumOffset(sum);
    splitPQ();
  }

  /**
   * @param sum
   */
  private void addSumOffset(BigDecimal sum) {
    BigDecimal modVal = new BigDecimal(0);
    int modIndex = -1;
    for (int i = 0; i < pq.length; i++) {
      if (pq[i].compareTo(modVal) > 0) {
        modVal = pq[i];
        modIndex = i;
      }
    }

    if (modIndex != -1) {
      pq[modIndex] = pq[modIndex].add(BigDecimal.valueOf(1).subtract(sum));
    }
  }
}
