import java.util.Random;

/**
 *
 */
public class ProbabilityGeneratorDouble extends ProbabilityGenerator<Double> {

  public ProbabilityGeneratorDouble(int n) {
    p = new Double[n];
    q = new Double[n + 1];
    pq = generateRandom((n * 2) + 1);

    generateProbabilities();
  }

  /**
   * @param n
   * @return
   */
  public Double[] generateRandom(int n) {
    Random rand = new Random();
    Double[] arr = new Double[n];

    for (int i = 0; i < n; i++) {
      arr[i] = (double) rand.nextInt(Integer.MAX_VALUE);
    }

    return arr;
  }

  /**
   *
   */
  public void generateProbabilities() {

    double sum = 0;
    for (double v : pq) {
      sum += v;
    }
    for (int i = 0; i < pq.length; i++) {
      pq[i] = pq[i] / sum;
    }

    sum = 0;
    for (double v : pq) {
      sum += v;
    }

    addSumOffset(sum);
    splitPQ();
  }

  /**
   * @param sum
   */
  private void addSumOffset(double sum) {
    double modVal = 0;
    int modIndex = -1;
    for (int i = 0; i < pq.length; i++) {
      if (pq[i] > modVal) {
        modVal = pq[i];
        modIndex = i;
      }
    }

    if (modIndex != -1) {
      pq[modIndex] += 1 - sum;
    }
  }

  void splitPQ() {
    for (int i = 0; i < p.length; i++) {
      p[i] = pq[i];
    }
    for (int i = 0; i < q.length; i++) {
      q[i] = pq[i + p.length];
    }
  }
}
