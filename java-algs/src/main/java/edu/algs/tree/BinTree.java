package edu.algs.tree;

import java.util.function.Consumer;

public class BinTree<T extends Comparable<T>> {
    private final boolean balanced;
    private Node<T> root = null;

    BinTree(boolean balanced) { this.balanced = balanced; }
    BinTree(boolean balanced, T val) { this.balanced = balanced; root = Node.value(null, val); }

    Node<T> getRoot() { return root; }

    public boolean contains(T val) {
        if (root == null) return false;
        Node<T> at = root;
        while (at != null) {
            if (at.val.equals(val)) return true;
            else at = less(val, at.val) ? at.left : at.right;
        }
        return false;
    }

    public void insert(T val) {
        if (root == null) { root = Node.value(null, val); return; }
        if (root.val.equals(val)) return;

        Node<T> at = root;  // parent of to be inserted node
        Node<T> to;         // the link where node will be inserted
        while ( (to = (less(val, at.val) ? at.left : at.right)) != null ) {
            if (to.val.equals(val)) return;
            at = to;
        }

        Node<T> node = Node.value(at, val);
        if (less(val, at.val)) at.left = node; else at.right = node;

        Node<T> n = node;
        while (!n.isRoot()) {
            n.parent.size += 1;
            n.parent.dep = 1 + Math.max(
                    (n.parent.left == null ? 0 : n.parent.left.dep),
                    (n.parent.right == null ? 0 : n.parent.right.dep));
            n = n.parent;
        }

        if (balanced) rebalance(node);
    }

    void update(Node<T> node, Node<T> branch) {
        if (node == null) root = branch;
        else if (less(branch.val, node.val)) node.left = branch; else node.right = branch;
    }

    // Detect if re-balancing required for a given subtree and perform the rotation
    void rebalance(Node<T> n) {
        Node<T> p = n.parent;   if (p == null) return;
        Node<T> pp = p.parent;  if (pp == null) return;
        Node<T> r = pp.parent;

        int pld = (p.left == null) ? 0 : p.left.dep;
        int prd = (p.right == null) ? 0 : p.right.dep;

        int ppld = (pp.left == null) ? 0 : pp.left.dep;
        int pprd = (pp.right == null) ? 0 : pp.right.dep;

        if (ppld == 2 && pprd == 0) {
            if (pld > prd) {
                /*
                        LL rotation:
                        |          |
                   pp-> C    =>    B
                       /          / \
                  p-> B          A  C
                     /
                n-> A
                 */
                p.left = n;     p.right = pp;    p.parent = r;
                n.left = null;  n.right = null;  n.parent = p;
                pp.left = null; pp.right = null; pp.parent = p;
                update(r, p);
            } else {
                /*
                        LR rotation:
                        |          |
                   pp-> C    =>    A
                       /          / \
                  p-> B          B  C
                       \
                    n-> A
                 */
                n.left = p;     n.right = pp;    n.parent = r;
                p.left = null;  p.right = null;  p.parent = n;
                pp.left = null; pp.right = null; pp.parent = n;
                update(r, n);
            }
        } else if (ppld == 0 && pprd == 2) {
            if (pld > prd) {
                /*
                        RL rotation:
                        |          |
                   pp-> C    =>    A
                         \        / \
                     p-> B       C  B
                        /
                   n-> A
                 */
                n.left = pp;    n.right = p;     n.parent = r;
                p.left = null;  p.right = null;  p.parent = n;
                pp.left = null; pp.right = null; pp.parent = n;
                update(r, n);
            } else {
                /*
                        RR rotation:
                        |          |
                   pp-> C    =>    B
                         \        / \
                      p-> B      C  A
                           \
                        n-> A
                 */
                p.left = pp;    p.right = n;     p.parent = r;
                pp.left = null; pp.right = null; pp.parent = p;
                n.left = null;  n.right = null;  n.parent = p;
                update(r, p);
            }
        }
    }

    public int size() { return (root == null ? 0 : root.size); }

    public int depth() { return (root == null ? 0 : root.dep); }

    public boolean isEmpty() { return root == null; }

    public void inorder(Consumer<T> handler) { inorder(root, handler); }

    private void inorder(Node<T> from, Consumer<T> handler) {
        if (from == null) return;
        if (from.left != null) inorder(from.left, handler);
        handler.accept(from.val);
        if (from.right != null) inorder(from.right, handler);
    }

    public void preorder(Consumer<T> handler) { preorder(root, handler); }

    private void preorder(Node<T> from, Consumer<T> handler) {
        if (from == null) return;
        handler.accept(from.val);
        if (from.left != null) preorder(from.left, handler);
        if (from.right != null) preorder(from.right, handler);
    }

    public void postorder(Consumer<T> handler) { postorder(root, handler); }

    private void postorder(Node<T> from, Consumer<T> handler) {
        if (from == null) return;
        if (from.left != null) postorder(from.left, handler);
        if (from.right != null) postorder(from.right, handler);
        handler.accept(from.val);
    }

    public void reverse() {
        root = reverse(root);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    private Node<T> reverse(Node<T> node) {
        if (node == null) return node;
        Node<T> l = reverse(node.right);
        Node<T> r = reverse(node.left);
        node.right = r;
        node.left = l;
        return node;
    }

    private boolean less(T lhs, T rhs) { return lhs.compareTo(rhs) < 0; }

    static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        final T val;
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        int size;
        int dep;

        Node(Node<T> parent, T val) {
            this.parent = parent;
            this.val = val;
            this.size = 1;
            this.dep = 1;
        }

        static <T extends Comparable<T>> Node<T> value(Node<T> parent, T value) { return new Node<>(parent, value); }

        boolean isRoot() { return parent == null; }
        boolean isLeaf() { return left == null && right == null; }

        @Override
        public int compareTo(Node<T> that) { return this.val.compareTo(that.val); }
    }

    public static <T extends Comparable<T>> BinTree<T> empty() { return new BinTree<>(false); }
    public static <T extends Comparable<T>> BinTree<T> empty(boolean balanced) { return new BinTree<>(balanced); }
    public static <T extends Comparable<T>> BinTree<T> one(T val) { return new BinTree<>(true, val); }
    public static <T extends Comparable<T>> BinTree<T> one(boolean balanced, T val) { return new BinTree<>(balanced, val); }
}
