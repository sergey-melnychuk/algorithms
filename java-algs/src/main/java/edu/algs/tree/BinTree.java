package edu.algs.tree;

import java.util.function.Consumer;

public class BinTree<T extends Comparable<T>> {
    private Node<T> root = null;

    private BinTree() {}
    private BinTree(T val) { root = Node.value(null, val); }

    public boolean contains(T val) {
        if (root == null) return false;
        Node<T> at = root;
        while (at != null) {
            if (at.val.equals(val)) return true;
            else at = (less(val, at.val)) ? at.left : at.right;
        }
        return false;
    }

    public void insert(T val) {
        if (root == null) {
            root = Node.value(null, val);
            return;
        }
        Node<T> to = root;
        while (!to.val.equals(val) && !to.isLeaf()) {
            balance(to, val);
            to = (less(val, to.val) ? to.left : to.right);
        }
        if (to.val.equals(val)) return;

        Node<T> n = Node.value(to, val);
        if (less(val, to.val)) to.left = n; else to.right = n;
        while (!n.isRoot()) {
            n.parent.size += 1;
            n.parent.dep = 1 + Math.max(
                    (n.parent.left == null ? 0 : n.parent.left.dep),
                    (n.parent.right == null ? 0 : n.parent.right.dep));
            n = n.parent;
        }
    }

    void balance(Node<T> at, T val) {
        int l = (at.left  == null ? 0 :  at.left.size) + (less(val, at.val) ? 1 : 0);
        int r = (at.right == null ? 0 : at.right.size) + (less(val, at.val) ? 0 : 1);
        if (l-r > 1 || r-l > 1) {
            // re-balancing is required to avoid redundant depth growth
            // TODO implement re-balancing
        }
    }

    public int size() { return (root == null ? 0 : root.size); }

    public int depth() { return (root == null ? 0 : root.dep); }

    public boolean isEmpty() { return root == null; }

    public void inorder(Consumer<T> handler) { inorder(root, handler); }

    void inorder(Node<T> from, Consumer<T> handler) {
        if (from == null) return;
        if (from.left != null) inorder(from.left, handler);
        handler.accept(from.val);
        if (from.right != null) inorder(from.right, handler);
    }

    public void preorder(Consumer<T> handler) { preorder(root, handler); }

    void preorder(Node<T> from, Consumer<T> handler) {
        if (from == null) return;
        handler.accept(from.val);
        if (from.left != null) preorder(from.left, handler);
        if (from.right != null) preorder(from.right, handler);
    }


    public void postorder(Consumer<T> handler) { postorder(root, handler); }

    void postorder(Node<T> from, Consumer<T> handler) {
        if (from == null) return;
        if (from.left != null) postorder(from.left, handler);
        if (from.right != null) postorder(from.right, handler);
        handler.accept(from.val);
    }

    boolean less(T lhs, T rhs) { return lhs.compareTo(rhs) < 0; }

    static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        private final Node<T> parent;
        private final T val;
        private int dep;
        private int size;
        private Node<T> left;
        private Node<T> right;

        private Node(Node<T> parent, T val) {
            this.parent = parent;
            this.val = val;
            this.size = 1;
            this.dep = 1;
        }

        static <T extends Comparable<T>> Node<T> value(Node<T> parent, T value) { return new Node<>(parent, value); }

        boolean isRoot() { return parent == null; }
        boolean isNode() { return ! isLeaf(); }
        boolean isLeaf() { return left == null && right == null; }
        boolean isEmpty() { return val == null; }

        @Override
        public int compareTo(Node<T> that) { return this.val.compareTo(that.val); }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> BinTree<T> empty() { return new BinTree<>(); }
    public static <T extends Comparable<T>> BinTree<T> one(T val) { return new BinTree<>(val); }
}
