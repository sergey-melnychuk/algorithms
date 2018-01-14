package edu.algs.tree;

import edu.algs.array.Array;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BSTreeTest {

    /*
        |    =>    |
        3          2
       /          / \
      2          1  3
     /
    1
     */
    @Test
    void moveL() {
        BSTree<Integer> bt = mkTree(3, 2, 1);
        Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertEquals(3, R.size, "size");
        assertEquals(2, R.dep, "depth");
        assertNotNull(R.lo);
        assertEquals(Integer.valueOf(1), R.lo.val);
        assertEquals(1, R.lo.size);
        assertEquals(1, R.lo.dep);
        assertNotNull(R.hi);
        assertEquals(Integer.valueOf(3), R.hi.val);
        assertEquals(1, R.hi.size);
        assertEquals(1, R.hi.dep);
    }

    /*
        |   =>     |
        1          2
         \        / \
          2      1  3
           \
            3
     */
    @Test
    void moveH() {
        BinTree<Integer> bt = mkTree(1, 2, 3);
        assertEquals(2, bt.depth());
        Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertEquals(3, R.size);
        assertEquals(2, R.dep);
        assertNotNull(R.lo);
        assertEquals(Integer.valueOf(1), R.lo.val);
        assertEquals(1, R.lo.size);
        assertEquals(1, R.lo.dep);
        assertNotNull(R.hi);
        assertEquals(Integer.valueOf(3), R.hi.val);
        assertEquals(1, R.hi.size);
        assertEquals(1, R.hi.dep);
    }

    /*
        |    =>    |
        3          2
       /          / \
      1          1  3
       \
        2
     */
    @Test
    void moveLH() {
        BinTree<Integer> bt = mkTree(3, 1, 2);
        assertEquals(2, bt.depth());
        Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.lo);
        assertEquals(Integer.valueOf(1), R.lo.val);
        assertNotNull(R.hi);
        assertEquals(Integer.valueOf(3), R.hi.val);
    }

    /*
        |    =>    |
        1          2
         \        / \
          3      1  3
         /
        2
     */
    @Test
    void moveHL() {
        BinTree<Integer> bt = mkTree(1, 3, 2);
        assertEquals(2, bt.depth());
        Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.lo);
        assertEquals(Integer.valueOf(1), R.lo.val);
        assertNotNull(R.hi);
        assertEquals(Integer.valueOf(3), R.hi.val);
    }

    @Test @Disabled
    void balance3l() {
        final int d = 3; // 3 full layers of the tree
        int n = (1 << d) - 1;
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test @Disabled
    void balance10l() {
        final int d = 10; // 10 full layers of the tree
        int n = (1 << d) - 1;
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test @Disabled
    void balance3e() {
        final int d = 2;
        final int n = 3; // 3 elements in the tree
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test @Disabled
    void balance15e() {
        final int d = 4;
        final int n = 15; // 15 elements in the tree
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test @Disabled
    void random4l() { random(4, 1); }

    @Test @Disabled
    void random10l() { random(10, 1); }

    private void random(int d, int relaxation) { random(d, relaxation, false); }

    private void random(int d, int relaxation, boolean debug) {
        int n = (1 << d) - 1;
        BinTree<Integer> tree = BinTree.empty();
        int a[] = mkRandom(n);
        for (int i=0; i<n; i++) {
            tree.insert(a[i]);
            if (debug) {
                System.out.println("insert(" + a[i] + ")");
                System.out.println(tree.shape(3));
            }
        }
        assertEquals(n, tree.size());
        int e = d + relaxation;
        assertTrue(e >= tree.depth(), String.format("Got %d, expected <%d", tree.depth(), e));

        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);

        Arrays.sort(a);
        int b[] = new int[n];
        for (int i=0; i<n; i++) b[i] = actual.get(i);

        assertArrayEquals(a, b);
    }

    private static int[] mkRandom(int n) {
        Random random = new Random(42L);
        int a[] = new int[n];
        for (int i=0; i<n; i++) a[i] = i;
        for (int i=0; i<n-2; i++) {
            int j = i + 1 + random.nextInt(n-2-i);
            Array.swap(a, i, j);
        }
        return a;
    }

    static BSTree<Integer> mkTree(int ...items) {
        BSTree<Integer> tree = new BSTree<>();
        for (int i : items) tree.insert(i);
        return tree;
    }
}
