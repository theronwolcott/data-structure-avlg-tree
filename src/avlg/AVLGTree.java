package avlg;

import avlg.exceptions.UnimplementedMethodException;
import avlg.exceptions.EmptyTreeException;
import avlg.exceptions.InvalidBalanceException;

/**
 * <p>
 * {@link AVLGTree} is a class representing an
 * <a href="https://en.wikipedia.org/wiki/AVL_tree">AVL Tree</a> with
 * a relaxed balance condition. Its constructor receives a strictly positive
 * parameter which controls the <b>maximum</b>
 * imbalance allowed on any subtree of the tree which it creates. So, for
 * example:
 * </p>
 * <ul>
 * <li>An AVL-1 tree is a classic AVL tree, which only allows for perfectly
 * balanced binary
 * subtrees (imbalance of 0 everywhere), or subtrees with a maximum imbalance of
 * 1 (somewhere).</li>
 * <li>An AVL-2 tree relaxes the criteria of AVL-1 trees, by also allowing for
 * subtrees
 * that have an imbalance of 2.</li>
 * <li>AVL-3 trees allow an imbalance of 3.</li>
 * <li>...</li>
 * </ul>
 *
 * <p>
 * The idea behind AVL-G trees is that rotations cost time, so maybe we would be
 * willing to
 * accept bad search performance now and then if it would mean less rotations.
 * On the other hand, increasing
 * the balance parameter also means that we will be making <b>insertions</b>
 * faster.
 * </p>
 *
 * @author YOUR NAME HERE!
 *
 * @see EmptyTreeException
 * @see InvalidBalanceException
 * @see StudentTests
 */
public class AVLGTree<T extends Comparable<T>> {

    /*
     * ********************************************************* *
     * Write any private data elements or private methods here...*
     * *********************************************************
     */
    private int g;
    private int size;
    private Node root;

    private class Node {
        int height;
        T data;
        Node leftChild;
        Node rightChild;

        public Node(T data) {
            this.data = data;
            this.height = 0;
        }
    }

    /*
     * ******************************************************** *
     * ************************ PUBLIC METHODS **************** *
     * ********************************************************
     */

    /**
     * The class constructor provides the tree with the maximum imbalance allowed.
     * 
     * @param maxImbalance The maximum imbalance allowed by the AVL-G Tree.
     * @throws InvalidBalanceException if maxImbalance is a value smaller than 1.
     */
    public AVLGTree(int maxImbalance) throws InvalidBalanceException {
        if (maxImbalance < 1) {
            throw new InvalidBalanceException("Invalid balance");
        }
        g = maxImbalance;
        size = 0;
    }

    private int getBalance(Node n) {
        if (n == null) {
            return 0;
        }
        return getHeight(n.leftChild) - getHeight(n.rightChild);
    }

    private Node rotateLeft(Node x) {
        Node y = x.rightChild;
        Node z = y.leftChild;
        x.rightChild = z;
        y.leftChild = x;
        x.height = Math.max(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        y.height = Math.max(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;
        return y;
    }

    private Node rotateRight(Node x) {
        Node y = x.leftChild;
        Node z = y.rightChild;
        x.leftChild = z;
        y.rightChild = x;
        x.height = Math.max(getHeight(x.leftChild), getHeight(x.rightChild)) + 1;
        y.height = Math.max(getHeight(y.leftChild), getHeight(y.rightChild)) + 1;
        return y;
    }

    private Node insert(Node n, T key) {
        if (n == null) {
            size++;
            return new Node(key);
        }
        if (key.compareTo(n.data) == 0) {
            return n;
        }
        if (key.compareTo(n.data) < 0) {
            n.leftChild = insert(n.leftChild, key);
        } else {
            n.rightChild = insert(n.rightChild, key);
        }

        // set height
        n.height = Math.max(getHeight(n.leftChild), getHeight(n.rightChild)) + 1;
        int balance = getBalance(n);
        // LL
        if (balance > g && key.compareTo(n.leftChild.data) < 0) {
            // rotate right
            return rotateRight(n);
        }
        // RR
        if (balance < -g && key.compareTo(n.rightChild.data) > 0) {
            // rotate left
            return rotateLeft(n);
        }
        // LR
        if (balance > g && key.compareTo(n.leftChild.data) > 0) {
            // rotate leftChild left
            n.leftChild = rotateLeft(n.leftChild);
            // rotate right
            return rotateRight(n);
        }
        // RL
        if (balance < -g && key.compareTo(n.rightChild.data) < 0) {
            // rotate rightChild right
            n.rightChild = rotateRight(n.rightChild);
            // rotate left
            return rotateLeft(n);
        }
        return n;
    }

    /**
     * Insert key in the tree. You will <b>not</b> be tested on
     * duplicates! This means that in a deletion test, any key that has been
     * inserted and subsequently deleted should <b>not</b> be found in the tree!
     * s
     * 
     * @param key The key to insert in the tree.
     */
    public void insert(T key) {
        root = insert(root, key);
    }

    /**
     * Delete the key from the data structure and return it to the caller.
     * 
     * @param key The key to delete from the structure.
     * @return The key that was removed, or {@code null} if the key was not found.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T delete(T key) throws EmptyTreeException {
        if (isEmpty()) {
            throw new EmptyTreeException("Empty tree");
        }
        Return ret = new Return();
        root = delete(root, key, ret);
        return ret.data;
    }

    private class Return {
        T data;
    }

    private Node delete(Node n, T key, Return ret) {
        if (n == null) {
            return null;
        }
        int comp = key.compareTo(n.data);
        Node inOrder;
        if (comp < 0) {
            n.leftChild = delete(n.leftChild, key, ret);
        } else if (comp > 0) {
            n.rightChild = delete(n.rightChild, key, ret);
        } else {
            // I am the node you're looking for
            ret.data = n.data;
            if (n.leftChild == null && n.rightChild == null) {
                size--;
                return null;
            }
            if (n.leftChild == null || n.rightChild == null) {
                if (n.leftChild == null) {
                    size--;
                    return n.rightChild;
                } else {
                    size--;
                    return n.leftChild;
                }
            } else {
                inOrder = getInOrderNode(n.rightChild);
                n.data = inOrder.data;
                Return r = new Return();
                n.rightChild = delete(n.rightChild, n.data, r);
            }
        }
        // set height
        n.height = Math.max(getHeight(n.leftChild), getHeight(n.rightChild)) + 1;
        int balance = getBalance(n);
        // LL
        if (balance > g && getBalance(n.leftChild) >= 0) {
            // rotate right
            return rotateRight(n);
        }
        // RR
        if (balance < -g && getBalance(n.rightChild) <= 0) {
            // rotate left
            return rotateLeft(n);
        }
        // LR
        if (balance > g && getBalance(n.leftChild) < 0) {
            // rotate leftChild left
            n.leftChild = rotateLeft(n.leftChild);
            // rotate right
            return rotateRight(n);
        }
        // RL
        if (balance < -g && getBalance(n.rightChild) > 0) {
            // rotate rightChild right
            n.rightChild = rotateRight(n.rightChild);
            // rotate left
            return rotateLeft(n);
        }

        return n;
    }

    private Node getInOrderNode(Node n) {
        if (n.leftChild == null) {
            return n;
        }
        return getInOrderNode(n.leftChild);
    }

    private T search(Node n, T key) {
        if (n == null) {
            return null;
        }
        int comp = key.compareTo(n.data);
        if (comp == 0) {
            return key;
        } else if (comp < 0) {
            return search(n.leftChild, key);
        } else {
            return search(n.rightChild, key);
        }
    }

    /**
     * <p>
     * Search for key in the tree. Return a reference to it if it's in there,
     * or {@code null} otherwise.
     * </p>
     * 
     * @param key The key to search for.
     * @return key if key is in the tree, or {@code null} otherwise.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T search(T key) throws EmptyTreeException {
        if (isEmpty()) {
            throw new EmptyTreeException("Empty tree");
        }
        return search(root, key);
    }

    private int getMaxImbalance(Node n) {
        if (n == null) {
            return 0;
        }
        int max = getBalance(n);
        int leftBalance = getMaxImbalance(n.leftChild);
        int rightBalance = getMaxImbalance(n.rightChild);
        if (Math.abs(leftBalance) > Math.abs(max)) {
            max = leftBalance;
        }
        if (Math.abs(rightBalance) > Math.abs(max)) {
            max = rightBalance;
        }
        return max;
    }

    /**
     * Retrieves the maximum imbalance parameter.
     * 
     * @return The maximum imbalance parameter provided as a constructor parameter.
     */
    public int getMaxImbalance() {
        return getMaxImbalance(root);
    }

    private int getHeight(Node n) {
        if (n == null) {
            return -1;
        }
        return n.height;
    }

    /**
     * <p>
     * Return the height of the tree. The height of the tree is defined as the
     * length of the
     * longest path between the root and the leaf level. By definition of path
     * length, a
     * stub tree has a height of 0, and we define an empty tree to have a height of
     * -1.
     * </p>
     * 
     * @return The height of the tree. If the tree is empty, returns -1.
     */
    public int getHeight() {
        if (isEmpty()) {
            return -1;
        }
        return root.height;
    }

    /**
     * Query the tree for emptiness. A tree is empty iff it has zero keys stored.
     * 
     * @return {@code true} if the tree is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return (size == 0); // ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    /**
     * Return the key at the tree's root node.
     * 
     * @return The key at the tree's root node.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T getRoot() throws EmptyTreeException {
        if (isEmpty()) {
            throw new EmptyTreeException("Empty tree");
        }
        return root.data;
    }

    /**
     * <p>
     * Establishes whether the AVL-G tree <em>globally</em> satisfies the BST
     * condition. This method is
     * <b>terrifically useful for testing!</b>
     * </p>
     * 
     * @return {@code true} if the tree satisfies the Binary Search Tree property,
     *         {@code false} otherwise.
     */
    public boolean isBST() {
        return isBST(root);
    }

    private boolean isBST(Node n) {
        if (n == null) {
            return true;
        }
        boolean left = n.leftChild == null || (n.data.compareTo(n.leftChild.data) > 0);
        boolean right = n.rightChild == null || (n.data.compareTo(n.rightChild.data) < 0);
        return left && right && isBST(n.leftChild) && isBST(n.rightChild);

    }

    /**
     * <p>
     * Establishes whether the AVL-G tree <em>globally</em> satisfies the AVL-G
     * condition. This method is
     * <b>terrifically useful for testing!</b>
     * </p>
     * 
     * @return {@code true} if the tree satisfies the balance requirements of an
     *         AVLG tree, {@code false}
     *         otherwise.
     */
    public boolean isAVLGBalanced() {
        return isAVLGBalanced(root);
    }

    private boolean isAVLGBalanced(Node n) {
        if (n == null) {
            return true;
        }
        return (Math.abs(getBalance(n)) <= g) && (isAVLGBalanced(n.leftChild) && isAVLGBalanced(n.rightChild));
    }

    /**
     * <p>
     * Empties the AVL-G Tree of all its elements. After a call to this method, the
     * tree should have <b>0</b> elements.
     * </p>
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * <p>
     * Return the number of elements in the tree.
     * </p>
     * 
     * @return The number of elements in the tree.
     */
    public int getCount() {
        return size;
    }
}
