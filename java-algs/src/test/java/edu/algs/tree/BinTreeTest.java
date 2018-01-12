package edu.algs.tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @Disabled //FIXME requires re-balancing implementation
    void preorder() {
        BinTree<Integer> tree = mkTree(true,1,2,3,4,5,6,7);
        List<Integer> expected = Arrays.asList(4,2,6,1,3,5,7);
        List<Integer> actual = new ArrayList<>();
        tree.preorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    @Disabled //FIXME requires re-balancing implementation
    void postorder() {
        BinTree<Integer> tree = mkTree(true,1,2,3,4,5,6,7);
        List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7);
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
        BinTree<Integer> bt = mkTree(false, 3, 2, 1);
        bt.rebalance(bt.getRoot().left.left);
        assertEquals(2, bt.depth());

        BinTree.Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.left);
        assertEquals(Integer.valueOf(1), R.left.val);
        assertNotNull(R.right);
        assertEquals(Integer.valueOf(3), R.right.val);
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
    void rebalanceLR() {
        BinTree<Integer> bt = mkTree(false, 3, 1, 2);
        bt.rebalance(bt.getRoot().left.right);
        //assertEquals(2, bt.depth());

        BinTree.Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.left);
        assertEquals(Integer.valueOf(1), R.left.val);
        assertNotNull(R.right);
        assertEquals(Integer.valueOf(3), R.right.val);
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
    void rebalanceRL() {
        BinTree<Integer> bt = mkTree(false, 1, 3, 2);

        bt.rebalance(bt.getRoot().right.left);
        //assertEquals(2, bt.depth());

        BinTree.Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.left);
        assertEquals(Integer.valueOf(1), R.left.val);
        assertNotNull(R.right);
        assertEquals(Integer.valueOf(3), R.right.val);
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
        BinTree<Integer> bt = mkTree(false, 1, 2, 3);

        bt.rebalance(bt.getRoot().right.right);
        assertEquals(2, bt.depth());

        BinTree.Node<Integer> R = bt.getRoot();
        assertEquals(Integer.valueOf(2), R.val);
        assertNotNull(R.left);
        assertEquals(Integer.valueOf(1), R.left.val);
        assertNotNull(R.right);
        assertEquals(Integer.valueOf(3), R.right.val);
    }

    @Test
    @Disabled // FIXME balancing of tree with 15 elements (4 full layers) doesn't work
    void balance4layers() {
        final int d = 4;
        int n = 0;
        for (int i=0; i<d; i++) n += (1 << i);

        BinTree<Integer> tree = BinTree.empty(true);
        for (int i=0; i<n; i++) tree.insert(i);

        assertEquals(d, tree.depth());
    }

    private BinTree<Integer> mkTree(int ...items) { return mkTree(false, items); }

    private BinTree<Integer> mkTree(boolean balanced, int ...items) {
        BinTree<Integer> tree = BinTree.empty(balanced);
        for (int i=0; i<items.length; i++) tree.insert(items[i]);
        return tree;
    }
}
