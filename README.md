# Optimal Binary Search Tree

OBST is a program that generates an optimal binary search tree from a file of keys. It can also generate sets of random keys in powers of 10, i.e. 10, 100, 1000, ..., 100,000.

It calculates and records the sum of all probabilities for all keys (and all non-existent keys) and stores it in the w[][] array. Using this table, it then calculates the expected cost of each key and stores the least expensive cost in the e[][] array, and it stores the index of the least expensive key in the root[][] array. Once it has the root, it then recursively repeats this process for each sub-tree, until each value range has an optimal key associated with it.

To put it another way: let us say [3,9] corresponds to keys 3 - 9. w[3][9] will store the sum of all probabilities of searching for keys 3-9 + the sum of all probabilities of searching for non-existent keys on either side of sub-trees with 3, 4, ..., 9 as the root. The root of this tree will always have a probability of 1, as it represents the sum of all probabilities of searching for any keys or non-existent key. e[3][9] will store the expected cost of the least expensive key to use as a root for the range 3-9. root[3][9] will store the index of the least expensive key at use as the root of the tree representing keys 3-9.

Once the OBST has been calculated, this program can construct it. It does this by first assigning the key at the index stored in root[1][n] to the root of the tree, which we will call r. It then finds the least-expensive key from 1 through r-1 and makes that the left child, then does the same with keys r+1 through n for the right child. Then, each of these children's children are found using this same process, which is repeated until the entire tree is constructed.

Note that, on average the run times with double values were as follows:
10 keys      = 1ms
100 keys     = 7ms
1000 keys    = 800ms - 1400ms
10,000 keys  = 2,700,000ms - 3,100,000ms, but sometimes terminated with OutOfMemoryError: java heap space
100,000 keys = Always terminated with OutOfMemoryError: java heap space

It should also be noted that the e and w matrices for 10,000 keys result in text files greater than 1GB in size.

## Structure

This project has several essential classes: 

- ProbabilityGenerator (Double or BigDecimal) - Generates random probability values for n keys and n+1 dummy keys such that all keys sum to 1.

- BST - The basic binary search tree data structure. Stores tree structure and data, and contains functions for printing the tree to the console.

- OBST (Double or BigDecimal) - Calculates the optimal binary search tree and constructs the result into a BST object.

- FileOperations - Performs various operations reading from and writing to files.

The program loops iterates NUM_SETS times, with n number of keys per set. The number of keys starts at n = MIN_KEYS and increases by n *= KEY_MULTIPLIER every loop. It then goes through one of two paths:

If the program was passed the argument "generate", it generates n keys, sorts them in ascending order, and saves them to data/keys/ascending/n.keys.txt. This operation can be considered initialization for the true function of the program: to generate the OBST.

If the program was passed the argument "obst", a ProbabilityGenerator object is created, which generates the p[] and q[] arrays which hold probabilities for the keys and their corresponding dummy keys. Then, an OBST object is created, which uses the keys and the probability arrays to generate the optimal structure of the BST. This computation time is recorded and saved to compTimes[]. A BST is then created with this optimal structure. Finally, the w, e, and root arrays; the compute times; and horizontally- and vertically-formatted trees are printed and saved to files.

## Design Decisions

The biggest decision was what data type to use to represent the probability values, and thus the e[][] and w[][] data types as well. The double data type processes more quickly but also has built-in imprecision, while BigDecimal is much more precise but has a heavier computational load for each operation. Thus, I created a generic class with sub-classes for both the OBST and the ProbabilityGenerator, which lets the programmer decide whether accuracy or speed is more important for their specific implementation. Note that making this generic structure resulted in a trade-off: generics cannot use primitive data types, so double values have to be boxed in Double objects. Double objects are significantly less efficient than double primitives, and using them to calculate the optimal tree is counter-productive. Thus, the Double arrays and matrices are converted to double primitives before calculation, then converted back to Double upon completion. This comes with an almost imperceptible performance cost and is superior to any alternative methods I explored, but it is a performance cost nonetheless.

For this project, I chose to use double, as n=10,000 took over 24 hours to process as BigDecimal and ultimately terminated with an OutOfMemoryError. Double took roughly 45 minutes to process n=10,000 but did so successfully. Neither implementation could process n=100,000 with my specific hardware configuration.

Another decision was made to make the tree itself (BST) its own class and create a separate class to do the optimization calculations and optimal tree construction. This could easily be combined into one all-encompassing class, but this would result in increased coupling. By separating the classes, BST can be used in scenarios where an optimized tree is unnecessary.

Because there is no historical search data for the keys, the probabilities of searching for any individual key did not exist. The decision was made to use generate these probabilities randomly. Theoretically, the sum of all probabilities should = 1; but given the imprecise nature of floating point arithmetic, this is not feasible with doubles. Thus, one should use BigDecimal if absolute precision is necessary. If using doubles, one must accept that the sum of all probabilities may be slightly greater than or less than 1. For large numbers of keys, this small amount of imprecision is generally worth the trade-off for a significant increase in performance. 

## Instructions

If no keys are present, run the program with the argument "generate" to generate key pairs of n=10, n=100, n=1000, n=10,000, and n=100,000.

Once keys have been generated, run the program with the argument "obst". That's it! The program will generate probabilities, calculate the optimal tree, and store the results to files. The files it generates are as follows:

- obstComputeTimes.txt - The number of milliseconds to compute each binary search tree. The first line is n=10, the second line is n=100, ..., the fifth line is n=100,000.

- output.ematrix.#.keys.txt - The e[][] matrix for the n=# key set.

- output.wmatrix.#.keys.txt - The w[][] matrix for the n=# key set.

- output.rootmatrix.#.keys.txt - The root[][] matrix for the n=# key set.

- output.tree.horizontal.#.keys.txt - The constructed optimal BST for the n=# key set, formatted horizontally. This is the traditional visual structure of a BST.

- output.tree.vertical.#.keys.txt - The constructed optimal BST for the n=# key set, formatted vertically. This format may be easier to read for larger numbers of keys.

