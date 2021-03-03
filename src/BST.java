import java.util.ArrayList;
import java.util.List;

/**
 * A typical Binary Search Tree data structure.
 */
public class BST {

  /**
   * A node in the binary search tree.
   */
  class Node {

    private int data;
    private Node left;
    public Node getLeft() { return left; }

    private Node right;
    public Node getRight() { return right; }

    public Node(int data) {
      this.data = data;
      left = null;
      right = null;
    }
  }

  /**
   * Searches for a value within the binary search tree.
   *
   * @param root The current node being iterated over.
   * @param data The value stored in the current node being iterated over.
   * @return
   */
  public Node search(Node root, int data) {
    if (root == null) {
      return root;
    } else if (root.data > data) {
      return search(root.left, data);
    } else if (root.data < data) {
      return search(root.right, data);
    } else {
      return root;
    }
  }

  private Node root;

  /**
   * Initializes an empty binary search tree.
   */
  BST() {
    root = null;
  }

  /**
   * Used externally to add a node to the binary search tree.
   *
   * @param data The value to add to the tree.
   */
  public void add(int data) {
    root = add(root, data);
  }

  /**
   * Provides the functionality to add a node to a binary search tree.
   * @param root The current node being iterated over.
   * @param data The value stored in the current node being iterated over.
   * @return
   */
  private Node add(Node root, int data) {
    if (root == null) {
      root = new Node(data);
    } else if (data < root.data) {
      root.left = add(root.left, data);
    } else if (data > root.data) {
      root.right = add(root.right, data);
    }

    return root;
  }

  /**
   * Provides the functionality for printVertically() to print the binary search tree.
   *
   * NOTE: This method was taken from:
   * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
   *
   * Since the function of this method is purely aesthetic and has no relation to the assignment
   * requirements, I did not see the harm in copying this code directly.
   * @param sb The string builder.
   * @param padding Corresponds to the number of nodes beneath the current node.
   * @param pointer Points to a tree node. If terminating, points right; else points right and down.
   * @param root The current node being iterated on.
   */
  private void traversePreOrder(StringBuilder sb, String padding, String pointer, Node root) {
    if (root != null) {
      sb.append(padding);
      sb.append(pointer);
      sb.append(root.data);
      sb.append("\n");

      StringBuilder paddingBuilder = new StringBuilder(padding);
      paddingBuilder.append("│  ");

      String paddingForBoth = paddingBuilder.toString();
      String pointerForRight = "└──";
      String pointerForLeft = (root.right != null) ? "├──" : "└──";

      traversePreOrder(sb, paddingForBoth, pointerForLeft, root.left);
      traversePreOrder(sb, paddingForBoth, pointerForRight, root.right);
    }
  }

  /**
   * Prints the binary search tree in a vertical format.
   */
  public void printVertical() {
    StringBuilder sb = new StringBuilder();
    traversePreOrder(sb, "", "", root);
    System.out.print(sb.toString());
  }

  /**
   * Prints the binary search tree in a horizontal format.
   * This works well for smaller trees, but large trees quickly become unreadable due to the amount
   * of horizontal scrolling that is needed.
   *
   * NOTE: This method was taken from:
   * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
   *
   * Since the function of this method is purely aesthetic and has no relation to the assignment
   * requirements, I did not see the harm in copying this code directly.
   */
  public void printHorizontal() {
    List<List<String>> lines = new ArrayList<>();
    List<Node> level = new ArrayList<>();
    List<Node> next = new ArrayList<>();

    level.add(root);
    int nn = 1;

    int widest = 0;

    while (nn != 0) {
      List<String> line = new ArrayList<>();

      nn = 0;

      for (Node n : level) {
        if (n == null) {
          line.add(null);

          next.add(null);
          next.add(null);
        } else {
          String aa = Integer.toString(root.data);
          line.add(aa);
          if (aa.length() > widest) {
            widest = aa.length();
          }

          next.add(n.getLeft());
          next.add(n.getRight());

          if (n.getLeft() != null) {
            nn++;
          }
          if (n.getRight() != null) {
            nn++;
          }
        }
      }

      if (widest % 2 == 1) {
        widest++;
      }

      lines.add(line);

      List<Node> tmp = level;
      level = next;
      next = tmp;
      next.clear();
    }

    int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
    for (int i = 0; i < lines.size(); i++) {
      List<String> line = lines.get(i);
      int hpw = (int) Math.floor(perpiece / 2f) - 1;

      if (i > 0) {
        for (int j = 0; j < line.size(); j++) {

          // split node
          char c = ' ';
          if (j % 2 == 1) {
            if (line.get(j - 1) != null) {
              c = (line.get(j) != null) ? '┴' : '┘';
            } else {
              if (j < line.size() && line.get(j) != null) {
                c = '└';
              }
            }
          }
          System.out.print(c);

          // lines and spaces
          if (line.get(j) == null) {
            for (int k = 0; k < perpiece - 1; k++) {
              System.out.print(" ");
            }
          } else {

            for (int k = 0; k < hpw; k++) {
              System.out.print(j % 2 == 0 ? " " : "─");
            }
            System.out.print(j % 2 == 0 ? "┌" : "┐");
            for (int k = 0; k < hpw; k++) {
              System.out.print(j % 2 == 0 ? "─" : " ");
            }
          }
        }
        System.out.println();
      }

      // print line of numbers
      for (int j = 0; j < line.size(); j++) {

        String f = line.get(j);
        if (f == null) {
          f = "";
        }
        int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
        int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

        // a number
        for (int k = 0; k < gap1; k++) {
          System.out.print(" ");
        }
        System.out.print(f);
        for (int k = 0; k < gap2; k++) {
          System.out.print(" ");
        }
      }
      System.out.println();

      perpiece /= 2;
    }
  }
}
