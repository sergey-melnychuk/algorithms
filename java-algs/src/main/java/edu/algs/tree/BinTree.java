package edu.algs.tree;

import java.util.*;
import java.util.function.Consumer;

// Binary Tree
public class BinTree<T extends Comparable<T>> {
    private Node<T> root = null;

    BinTree() {}
    BinTree(Node<T> root) { this.root = root; }

    public static <T extends Comparable<T>> BinTree<T> empty() { return new BinTree<>(); }

    public static <T extends Comparable<T>> BinTree<T> build(T a[]) {
        return new BinTree<>(build(a, 0, a.length-1, null));
    }

    public boolean contains(T val) {
        if (root == null) return false;
        Node<T> at = root;
        while (at != null && !at.val.equals(val)) {
            at = less(val, at.val) ? at.lo : at.hi;
        }
        return at != null;
    }

    public void insert(T val) {
        if (root == null) { root = Node.value(null, val); return; }
        if (root.val.equals(val)) return;

        Node<T> at = root;  // parent of to be inserted node
        Node<T> to;         // the link where node will be inserted
        while ( (to = (less(val, at.val) ? at.lo : at.hi)) != null ) {
            if (to.val.equals(val)) return;
            at = to;
        }

        Node<T> node = Node.value(at, val);
        put(node, at);

        postInsert(node);
    }

    public void inorder(Consumer<T> handler) { inorder(root, n -> handler.accept(n.val)); }

    public void preorder(Consumer<T> handler) { preorder(root, n -> handler.accept(n.val)); }

    public void postorder(Consumer<T> handler) { postorder(root, n -> handler.accept(n.val)); }

    public void bfs(Consumer<T> handler) { bfs(root, n -> handler.accept(n.val)); }

    public void dfs(Consumer<T> handler) { dfs(root, n -> handler.accept(n.val)); }

    public void reverse() { root = reverse(root); }

    public int size() { return (root == null ? 0 : root.size); }

    public int depth() { return (root == null ? 0 : root.dep); }

    public boolean isEmpty() { return root == null; }


    /* ********** Package-accessed internal methods ********** */


    void postInsert(Node<T> last) {}

    Node<T> getRoot() { return root; }
    void setRoot(Node<T> root) { this.root = root; }

    static <T extends Comparable<T>> Node<T> build(T a[], int lo, int hi, Node<T> parent) {
        if (lo > hi) return null;
        int m = lo + (hi-lo) / 2;
        Node<T> node = Node.value(parent, a[m]);
        node.lo = build(a, lo, m-1, node);
        node.hi = build(a, m+1, hi, node);
        node.touch();
        return node;
    }

    private boolean less(T lhs, T rhs) { return lhs.compareTo(rhs) < 0; }

    void put(Node<T> n, Node<T> p) {
        if (p == null) { root = n; return; }
        if (less(n.val, p.val)) p.lo = n; else p.hi = n;
        touch(n);
    }

    void touch(Node<T> n) {
        if (n == null) return;
        while (n != null) {
            n.touch();
            n = n.parent;
        }
    }

    Node<T> min() {
        Node<T> n = root;
        while (n.lo != null) n = n.lo;
        return n;
    }

    Node<T> max() {
        Node<T> n = root;
        while (n.hi != null) n = n.hi;
        return n;
    }

    void inorder(Node<T> from, Consumer<Node<T>> handler) {
        if (from == null) return;
        if (from.lo != null) inorder(from.lo, handler);
        handler.accept(from);
        if (from.hi != null) inorder(from.hi, handler);
    }

    void preorder(Node<T> from, Consumer<Node<T>> handler) {
        if (from == null) return;
        handler.accept(from);
        if (from.lo != null) preorder(from.lo, handler);
        if (from.hi != null) preorder(from.hi, handler);
    }

    void postorder(Node<T> from, Consumer<Node<T>> handler) {
        if (from == null) return;
        if (from.lo != null) postorder(from.lo, handler);
        if (from.hi != null) postorder(from.hi, handler);
        handler.accept(from);
    }

    void bfs(Node<T> from, Consumer<Node<T>> handler) {
        Queue<Node<T>> q = new LinkedList<>();
        q.add(from);
        while (!q.isEmpty()) {
            Node<T> n = q.poll();
            handler.accept(n);
            if (n.lo != null) q.offer(n.lo);
            if (n.hi != null) q.offer(n.hi);
        }
    }

    void dfs(Node<T> from, Consumer<Node<T>> handler) {
        Set<Node<T>> seen = new HashSet<>();
        Stack<Node<T>> stack = new Stack<>();
        stack.push(from);
        while (!stack.isEmpty()) {
            Node<T> n = stack.peek();
            if      (n.lo != null && !seen.contains(n.lo)) stack.push(n.lo);
            else if (n.hi != null && !seen.contains(n.hi)) stack.push(n.hi);
            else    { handler.accept(n); seen.add(n); stack.pop(); }
        }
    }

    private Node<T> reverse(Node<T> node) {
        if (node == null) return node;
        Node<T> l = node.lo;
        Node<T> r = node.hi;
        node.hi = reverse(l);
        node.lo = reverse(r);
        return node;
    }

    private int level(Node<T> node) {
        Node<T> n = node;
        int lvl = 0;
        while (n.parent != null) {
            lvl += 1;
            n = n.parent;
        }
        return lvl;
    }

    String shape(int w) {
        List<Node<T>> nodes = new ArrayList<>();
        inorder(root, nodes::add);

        int maxidx = nodes.size();
        int maxlvl = 0;
        for (Node<T> n : nodes) {
            int l = level(n);
            if (l > maxlvl) maxlvl = l;
        }

        StringBuilder sb0 = new StringBuilder();
        for (int i=0; i<w; i++) sb0.append(' ');
        String empty = sb0.toString();

        String tree[][] = new String[maxidx][maxlvl+1];
        for (int i=0; i<maxidx; i++) {
            int l = level(nodes.get(i));
            tree[i][l] = String.format("%" + w + "s", nodes.get(i).val.toString());
        }

        StringBuilder sb = new StringBuilder();
        for (int j=0; j<maxlvl+1; j++) {
            for (int i=0; i<maxidx; i++) {
                String s = tree[i][j];
                sb.append((s == null) ? empty : s);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
