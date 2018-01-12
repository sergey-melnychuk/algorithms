package edu.algs.tree;

import edu.algs.array.Array;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BinTreeTest {

    @Test
    void empty() {
        BinTree<Integer> tree = BinTree.empty();
        assertTrue(tree.isEmpty());
    }

    @Test
    void insertSingle() {
        BinTree<Integer> tree = BinTree.empty();
        assertFalse(tree.contains(1));
        tree.insert(1);
        assertTrue(tree.contains(1));
    }

    @Test
    void createOne() {
        BinTree<Integer> tree = BinTree.one(1);
        assertTrue(tree.contains(1));
    }

    @Test
    void size() {
        BinTree<Integer> tree = mkTree(1,2,3,4,5,6,7);
        assertEquals(7, tree.size());
    }

    @Test
    void depth7() {
        BinTree<Integer> tree = mkTree(1,2,3,4,5,6,7); // ends up as linked list
        assertEquals(7, tree.depth());
    }

    @Test
    void depth3() {
        BinTree<Integer> tree = mkTree(4,2,6,1,3,5,7); // ends up as balanced tree
        assertEquals(3, tree.depth());
    }

    @Test
    void duplicate1() {
        BinTree<Integer> tree = BinTree.one(1);
        for (int i=0; i<10; i++) tree.insert(1);
        assertEquals(1, tree.size());
    }

    @Test
    void duplicate7() {
        int items[] = {1,2,3,4,5,6,7};
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<items.length; i++) tree.insert(items[i]);
        for (int k=0; k<100; k++) for (int i=0; i<items.length; i++) tree.insert(items[i]);
        assertEquals(items.length, tree.size());
    }

    @Test
    void inorder() {
        BinTree<Integer> tree = mkTree(1,2,3,4,5,6,7);
        List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7);
        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void preorder() {
        BinTree<Integer> tree = mkTree(true,1,2,3,4,5,6,7);
        List<Integer> expected = Arrays.asList(4,2,1,3,6,5,7);
        List<Integer> actual = new ArrayList<>();
        tree.preorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void postorder() {
        BinTree<Integer> tree = mkTree(true,1,2,3,4,5,6,7);
        List<Integer> expected = Arrays.asList(1,3,2,5,7,6,4);
        List<Integer> actual = new ArrayList<>();
        tree.postorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void reverse() {
        BinTree<Integer> tree = mkTree(4,2,6,1,3,5,7); // ends up as balanced tree
        List<Integer> expected = Arrays.asList(7,6,5,4,3,2,1);
        tree.reverse();
        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);
        assertEquals(expected, actual);
    }

    /*

        |    =>    |
        3          2
       /          / \
      2          1  3
     /
    1
     */
    @Test
    void rebalanceLL() {
        BinTree<Integer> bt = mkTree(true, 3, 2, 1);
        BinTree.Node<Integer> R = bt.getRoot();
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
    RR rotation:
        |   =>     |
        1          2
         \        / \
          2      1  3
           \
            3
     */
    @Test
    void rebalanceRR() {
        BinTree<Integer> bt = mkTree(true, 1, 2, 3);
        assertEquals(2, bt.depth());
        BinTree.Node<Integer> R = bt.getRoot();
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
    LR rotation:
        |    =>    |
        3          2
       /          / \
      1          1  3
       \
        2
     */
    @Test
    @Disabled // FIXME hi-lo rotation doesn't detect such case
    void rebalanceLR() {
        BinTree<Integer> bt = mkTree(true, 3, 1, 2);
        assertEquals(2, bt.depth());
        BinTree.Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.lo);
        assertEquals(Integer.valueOf(1), R.lo.val);
        assertNotNull(R.hi);
        assertEquals(Integer.valueOf(3), R.hi.val);
    }

    /*
    RL rotation:
        |    =>    |
        1          2
         \        / \
          3      1  3
         /
        2
     */
    @Test
    @Disabled  // FIXME hi-lo rotation doesn't detect such case
    void rebalanceRL() {
        BinTree<Integer> bt = mkTree(true, 1, 3, 2);
        assertEquals(2, bt.depth());
        BinTree.Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.lo);
        assertEquals(Integer.valueOf(1), R.lo.val);
        assertNotNull(R.hi);
        assertEquals(Integer.valueOf(3), R.hi.val);
    }

    @Test
    void balance3l() {
        final int d = 3; // 3 full layers of the tree
        int n = (1 << d) - 1;
        BinTree<Integer> tree = BinTree.empty(true);
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test
    void balance10l() {
        final int d = 10; // 10 full layers of the tree
        int n = (1 << d) - 1;
        BinTree<Integer> tree = BinTree.empty(true);
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test
    void balance3e() {
        final int d = 2;
        final int n = 3; // 3 elements in the tree
        BinTree<Integer> tree = BinTree.empty(true);
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test
    void balance15e() {
        final int d = 4;
        final int n = 15; // 15 elements in the tree
        BinTree<Integer> tree = BinTree.empty(true);
        for (int i=0; i<n; i++) tree.insert(i);
        assertEquals(n, tree.size());
        assertEquals(d, tree.depth());
    }

    @Test
    void random4l() {
        final int d = 4; // 4 full layers
        int n = (1 << d) - 1;
        BinTree<Integer> tree = BinTree.empty(true);
        int a[] = mkRandom(n);
        for (int i=0; i<n; i++) {
            tree.insert(a[i]);
            System.out.println("insert(" + a[i] + ")");
            System.out.println(tree.shape(3));
        }
        assertEquals(n, tree.size());
        assertTrue(d+1 >= tree.depth(), "Tree depth outgrows balance max by 1 layer");

        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);

        Arrays.sort(a);
        int b[] = new int[n];
        for (int i=0; i<n; i++) b[i] = actual.get(i);

        assertArrayEquals(a, b);
    }

    @Test
    void build() {
        BinTree<Integer> bt = BinTree.sorted(new Integer[] {1, 2, 3});
        assertEquals(2, bt.depth());
        assertEquals(3, bt.size());

        assertEquals(Integer.valueOf(2), bt.getRoot().val);
        assertNotNull(bt.getRoot().lo);
        assertEquals(Integer.valueOf(1), bt.getRoot().lo.val);
        assertNotNull(bt.getRoot().hi);
        assertEquals(Integer.valueOf(3), bt.getRoot().hi.val);
    }

    private BinTree<Integer> mkTree(int ...items) { return mkTree(false, items); }

    private BinTree<Integer> mkTree(boolean balanced, int ...items) {
        BinTree<Integer> tree = BinTree.empty(balanced);
        for (int i=0; i<items.length; i++) tree.insert(items[i]);
        return tree;
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
}
