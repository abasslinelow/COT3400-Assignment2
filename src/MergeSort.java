import java.util.Arrays;

public class MergeSort {

  // Due to the recursive nature of the merge sort algorithm, this overridable function's purpose
  // is twofold:
  // 1) Allows the function to be called more simply, as the program does not have to pass the
  //      low and high for the original array, which will always be 0 and n-1.
  // 2) Since Java arrays are Objects, and Objects are passed as reference values, it allows the
  //      original array to be copied and returned without altering the original array. This is
  //      especially necessary due to the recursive nature of the algorithm.
  public static int[] mergeSort(int[] arr) {
    int[] arrSorted = Arrays.copyOf(arr, arr.length);
    mergeSort(arrSorted, 0, arr.length-1);
    return arrSorted;
  }

  // The "divide" part of divide and conquer, this function is responsible for splitting arrays
  // at the midpoint. It works recursively to split all elements until there are n subarrays, each
  // containing one element. It then calls merge(), which decides how to re-combine the array.
  private static void mergeSort(int[] arr, int low, int high) {
    // If the array has more than one element...
    if (low < high) {

      // Find the midpoint.
      int mid = (low + high) / 2;

      // Recursively split the left sub-array.
      mergeSort(arr, low, mid);

      // Recursively split the right sub-array.
      mergeSort(arr, mid + 1, high);

      // Recombine the arrays.
      merge(arr, low, mid, high);
    }
  }

  // The "conquer" part of divide and conquer, this function is responsible for re-combining the
  // subarrays back to an ordered array.
  private static void merge(int[] arr, int low, int mid, int high) {

    // Create empty arrays representing the sorted left and right sub-arrays.
    int n1 = mid - low + 1;
    int n2 = high - mid;

    // Add 1 to the n to account for a "sentinel value". This value represents infinite and will
    // be put on the bottom of each array. This value will always be greater than other elements.
    int[] L = new int[n1+1];
    int[] R = new int[n2+1];

    // Fill both left and right arrays with the values from the original array.
    for (int i=0; i < n1; i++) {
      L[i] = arr[low + i];
    }
    for (int j=0; j < n2; j++) {
      R[j] = arr[mid + 1 + j];
    }

    // Add the sentinel values to the left and right arrays.
    L[n1] = Integer.MAX_VALUE;
    R[n2] = Integer.MAX_VALUE;

    int lowIndex = 0;
    int highIndex = 0;

    // Starting with the lowest index, go through each elements of the original array and
    // add the lowest values first.
    for (int key = low; key <= high; key++) {

      // Compare the first element of the left array to the first element of the right array.
      // Whichever is smaller is put in the first element of the original array, and the index of
      // the array it belonged to is iterated. The other index is unchanged. The new value is then
      // compared against the value that "lost" the previous comparison. Eventually, one of the
      // arrays will hit the sentinel value. No more swapping is necessary, and the original array
      // is filled with the remaining elements of the array that did not hit its sentinel value.
      if (L[lowIndex] <= R[highIndex]) {
        arr[key] = L[lowIndex];
        lowIndex++;
      } else {
        arr[key] = R[highIndex];
        highIndex++;
      }
    }
  }

}
