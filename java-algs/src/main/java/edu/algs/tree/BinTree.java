package edu.algs.tree;

import java.util.*;
import java.util.function.Consumer;

public class BinTree<T extends Comparable<T>> {
    private final boolean balanced;
    private Node<T> root = null;

    BinTree(boolean balanced) { this.balanced = balanced; }
    BinTree(boolean balanced, T val) { this.balanced = balanced; root = Node.value(null, val); }

    Node<T> getRoot() { return root; }
    void setRoot(Node<T> root) { this.root = root; }

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

    public boolean contains(T val) {
        if (root == null) return false;
        Node<T> at = root;
        while (at != null) {
            if (at.val.equals(val)) return true;
            else at = less(val, at.val) ? at.lo : at.hi;
        }
        return false;
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
        put(node);
        stats(node);

        balance(node);
    }

    private void put(Node<T> n) { put(n.parent, n); }
    private void put(Node<T> p, Node<T> n) {
        if (p == null) root = n;
        else if (less(n.val, p.val)) p.lo = n; else p.hi = n;
    }

    private void stats(Node<T> from) { stats(from, root); }
    private void stats(Node<T> from, Node<T> to) {
        if (from == null) return;
        Node<T> n = from;
        while (n != to) {
            n.touch();
            n = n.parent;
        }
        to.touch();
    }

    /*                     C
        at=A      ->      / \
         /  \            A  cr
        /    \          / \
    B      to=C        B   cl
      / \    / \      / \
    bl  bh cl  ch   bl  bh
     */
    private Node<T> movehi(Node<T> at) {
        Node<T> to = at.hi;
        Node<T> p = at.parent;
        at.hi = to.lo;
        if (at.hi != null) at.hi.parent = at;
        to.lo = at;
        put(p, to);
        at.parent = to;
        to.parent = p;
        at.touch();
        stats(at);
        return to;
    }

    /*                     B
        at=A      ->      / \
         /  \           bl   A
        /    \              / \
    to=B      C           bh   C
      / \    / \              / \
    bl  bh cl  ch           cl  ch
     */
    private Node<T> movelo(Node<T> at) {
        Node<T> to = at.lo;
        Node<T> p = at.parent;
        at.lo = to.hi;
        if (at.lo != null) at.lo.parent = at;
        to.hi = at;
        put(p, to);
        at.parent = to;
        to.parent = p;
        at.touch();
        stats(at);
        return to;
    }

    // Detect if re-balancing required for a given subtree and perform the rotation
    void balance(Node<T> last) {
        if (!balanced) return;
        while (last != null) {
            if (last.balance() > 1) {
                last = movelo(last);
            } else if (last.balance() < -1) {
                last = movehi(last);
            }
            last = last.parent;
        }

        /*
                    LL rotation:
                |        ->        |
                C                  B
               / \               /   \
              /   \             /     \
             B    cr           A       C
            / \               / \     / \
           A  bl            al  ar  bl  cr
          / \
         al ar
         */
        // TODO implement LL rotation

        /*
                    LR rotation:
                |        ->        |
                C                  A
               / \               /   \
              /   \             /     \
             B    cr           B       C
            /  \              / \     / \
           bl   A           bl  al  ar  cr
               / \
              al ar
         */
        // TODO implement LR rotation

        /*
                    RL rotation:
                |        ->        |
                C                  A
               / \               /   \
              /   \             /     \
             cl    B           C       B
                  / \         / \     / \
                 A  br      cl  al   ar  br
                / \
               al ar

         */
        // TODO implement RL rotation

        /*
                    RR rotation:
                |        ->        |
                C                  B
               / \               /   \
              /   \             /     \
             cl    B           C       A
                  / \         / \     / \
                bl   A      cl  bl   al  ar
                    / \
                   al ar
         */
        // TODO implement RR rotation
    }

    public int size() { return (root == null ? 0 : root.size); }

    public int depth() { return (root == null ? 0 : root.dep); }

    public boolean isEmpty() { return root == null; }

    public void inorder(Consumer<T> handler) { inorder(root, n -> handler.accept(n.val)); }

    void inorder(Node<T> from, Consumer<Node<T>> handler) {
        if (from == null) return;
        if (from.lo != null) inorder(from.lo, handler);
        handler.accept(from);
        if (from.hi != null) inorder(from.hi, handler);
    }

    public void preorder(Consumer<T> handler) { preorder(root, n -> handler.accept(n.val)); }

    void preorder(Node<T> from, Consumer<Node<T>> handler) {
        if (from == null) return;
        handler.accept(from);
        if (from.lo != null) preorder(from.lo, handler);
        if (from.hi != null) preorder(from.hi, handler);
    }

    public void postorder(Consumer<T> handler) { postorder(root, n -> handler.accept(n.val)); }

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
        Set<T> seen = new HashSet<>();
        Stack<Node<T>> stack = new Stack<>();
        stack.push(from);
        while (!stack.isEmpty()) {
            Node<T> n = stack.peek();
            if      (n.lo != null && !seen.contains(n.lo.val)) stack.push(n.lo);
            else if (n.hi != null && !seen.contains(n.hi.val)) stack.push(n.hi);
            else    { handler.accept(n); seen.add(n.val); stack.pop(); }
        }
    }

    public void reverse() {
        root = reverse(root);
    }

    private Node<T> reverse(Node<T> node) {
        if (node == null) return node;
        Node<T> l = node.lo;
        Node<T> r = node.hi;
        node.hi = reverse(l);
        node.lo = reverse(r);
        return node;
    }

    private boolean less(T lhs, T rhs) { return lhs.compareTo(rhs) < 0; }

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

    boolean deepEquals(BinTree<T> that) {
        if (root == null) return that.root == null;
        return root.deepEquals(that.root);
    }

    static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        final T val;
        Node<T> parent;
        Node<T> lo;
        Node<T> hi;
        int size;
        int dep;

        Node(Node<T> parent, T val) {
            this.parent = parent;
            this.val = val;
            this.size = 1;
            this.dep = 1;
        }

        boolean isRoot() { return parent == null; }

        boolean isLeaf() { return (lo == null) && (hi == null); }

        int balance() { return ((lo == null) ? 0 : lo.dep) - ((hi == null) ? 0 : hi.dep); }

        void touch() {
            size = 1 + ((lo == null) ? 0 : lo.size) + ((hi == null) ? 0 : hi.size);
            dep = 1 + Math.max(((lo == null) ? 0 : lo.dep), ((hi == null) ? 0 : hi.dep));
        }

        @Override
        public int compareTo(Node<T> that) { return this.val.compareTo(that.val); }

        @Override
        public String toString() { return val.toString(); }

        boolean deepEquals(Node<T> that) {
            if (that == null) return false;
            return ((that.val == null && this.val == null) || that.val.equals(this.val)) &&
                    ((that.lo == null && this.lo == null) || that.lo.deepEquals(this.lo) ) &&
                    ((that.hi == null && this.hi == null) || that.hi.deepEquals(this.hi) ) &&
                    (that.size == this.size) &&
                    (that.dep == this.dep);
        }

        static <T extends Comparable<T>> Node<T> value(Node<T> parent, T value) { return new Node<>(parent, value); }
    }

    public static <T extends Comparable<T>> BinTree<T> empty() { return new BinTree<>(false); }
    public static <T extends Comparable<T>> BinTree<T> empty(boolean balanced) { return new BinTree<>(balanced); }
    public static <T extends Comparable<T>> BinTree<T> one(T val) { return new BinTree<>(true, val); }
    public static <T extends Comparable<T>> BinTree<T> one(boolean balanced, T val) { return new BinTree<>(balanced, val); }

    public static <T extends Comparable<T>> BinTree<T> sorted(T a[]) {
        Node<T> root = build(a, 0, a.length-1, null);
        BinTree<T> tree = new BinTree<>(true);
        tree.setRoot(root);
        return tree;
    }

    public static <T extends Comparable<T>> BinTree<T> build(T a[]) {
        Arrays.sort(a);
        return sorted(a);
    }

    static <T extends Comparable<T>> Node<T> build(T a[], int lo, int hi, Node<T> parent) {
        if (lo > hi) return null;
        int m = lo + (hi-lo) / 2;
        Node<T> node = Node.value(parent, a[m]);
        node.lo = build(a, lo, m-1, node);
        node.hi = build(a, m+1, hi, node);
        node.touch();
        return node;
    }
}
