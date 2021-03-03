// Author: Todd Bauer
// Last Modified: 1/27/2021
//
//

import java.io.IOException;
import java.util.Random;

/**
 * The driver class for the Optimal Binary Search Tree assignment.
 */
public class Main {

  private static int ARG_SORT_METHOD = 0; // The argument element that specifies sorting method.
  private static int LOW = 100000001;     // The low range of a key value.
  private static int HIGH = 999999999;    // The high range of a key value.
  private static int MIN_KEYS = 10;       // The minimum number of keys in a keyset.
  private static int KEY_MULTIPLIER = 10; // The multiple for each keyset.
  private static int NUM_SETS = 5;        // The number of keysets to iterate over.

  public static void main(String[] args) {

    // If no argument is supplied, alert the user with an error and terminate program.
    if (args.length <= 0) {
      System.out.println("No argument was given. If you wish to generate keys sorted in "
          + "ascending order, pass the argument \"generate\". If you wish to calculate "
          + "an optimal binary search tree, pass the argument \"obst\".");
      System.exit(0);
    } else {
      int[] arr;
      int n = MIN_KEYS;
      String path;

      OBSTDouble obst;

      long[] computeTimes = new long[NUM_SETS];
      long startTime;
      long endTime;
      long computeTime;

      // The process loop for every keyset.
      for (int i = 0; i < NUM_SETS; i++) {
        try {

          // Handle each command line argument.
          switch (args[ARG_SORT_METHOD]) {
            case "generate":
              arr = generateKeys(n, LOW, HIGH);
              arr = MergeSort.mergeSort(arr);
              path = "data/keys/ascending";
              FileOperations.arrToFile(arr, n + ".keys.txt");
              break;
            case "obst":
              arr = FileOperations.fileToArr("data/keys/ascending/" + n + ".keys.txt");
              path = "data/results";
              break;
            default:
              arr = null;
              path = "";
              System.out.println("An invalid argument was entered. If you wish to generate keys "
                  + "sorted in ascending order, pass the argument \"generate\". If you wish to "
                  + "calculate an optimal binary search tree, pass the argument \"obst\".");
              System.exit(0);
              break;
          }

          // If the user is not attempting to generate new keys, we want to create the OBST.
          if (args[ARG_SORT_METHOD].equals("obst")) {

            // Generate the probabilities for the real keys and dummy keys.
            ProbabilityGeneratorDouble probabilities = new ProbabilityGeneratorDouble(arr.length);

            // Run the OBST algorithm on the probabilities and calculate the time it takes to do so.
            startTime = System.currentTimeMillis();
            obst = new OBSTDouble(arr, probabilities.p, probabilities.q, n);
            obst.optimalBST();
            endTime = System.currentTimeMillis();
            computeTime = endTime - startTime;
            computeTimes[i] = computeTime;
            System.out
                .println("Time to find optimal BST of " + n + " keys: " + computeTime + "ms.");

            // Construct the tree based off the results of the OBST algorithm.
            obst.constructOBST(1, arr.length, -1);

            // Print all results to their respective files.
            FileOperations.arrToFile(obst.getE(),
                path + "/output.ematrix." + n + ".keys.txt",
                15, 10);
            FileOperations.arrToFile(obst.getW(),
                path + "/output.wmatrix." + n + ".keys.txt",
                15, 10);
            FileOperations.arrToFile(obst.getRoot(),
                path + "/output.rootmatrix." + n + ".keys.txt",
                8);
            FileOperations.arrToFile(computeTimes, path + "/obstComputeTimes.txt");
            FileOperations.treeToFile(obst, OBSTDouble.PrintValues.KEYS,
                OBSTDouble.PrintFormat.HORIZONTAL,
                path + "/output.tree.horizontal." + n + ".keys.txt");
            FileOperations.treeToFile(obst, OBSTDouble.PrintValues.KEYS,
                OBSTDouble.PrintFormat.VERTICAL,
                path + "/output.tree.vertical." + n + ".keys.txt");

          // If the user is generating keys, let them know when they are generated.
          } else if (args[0].equals("generate") && i >= NUM_SETS - 1) {
            System.out.println((i + 1) + " sets of random ascending sorted keys have been "
                + "generated. Please move these keys to the proper directory.\n"
                + "e.g. (projectdir)/keys/unsorted");
          }

          // Keys are increasing exponents of 10, so multiply the number of keys by 10.
          n *= KEY_MULTIPLIER;

          // Handle any thrown exceptions.
        } catch (IOException e) {
          System.out.println(e);
        } catch (FileOperations.IncorrectClassTypeException e) {
          System.out.println(e);
        }
      }
    }
  }

  // Generates an array with n elements. Each element contains a random value from low to high.
  private static int[] generateKeys(int n, int low, int high) {
    int[] arr = new int[n];

    for (int i = 0; i < n; i++) {
      Random rand = new Random();
      arr[i] = rand.nextInt(high - low) + low;
    }
    return arr;
  }
}
