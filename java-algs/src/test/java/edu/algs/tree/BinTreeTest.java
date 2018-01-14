package edu.algs.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinTreeTest {

    @Test
    void empty() {
        BinTree<Integer> tree = BinTree.empty();
        assertEquals(0, tree.size());
        assertEquals(0, tree.depth());
        assertTrue(tree.isEmpty());
    }

    @Test
    void nonempty() {
        BinTree<Integer> tree = BinTree.empty();
        tree.insert(1);
        assertEquals(1, tree.size());
        assertEquals(1, tree.depth());
        assertFalse(tree.isEmpty());
    }

    @Test
    void insert1() {
        BinTree<Integer> tree = BinTree.empty();
        tree.insert(1);
        assertTrue(tree.contains(1));
    }

    @Test
    void insertN() {
        final int N = 100;
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<N; i++) tree.insert(i);
        assertEquals(N, tree.size());
        assertEquals(N, tree.depth()); // linked list
        for (int i=0; i<N; i++) assertTrue(tree.contains(i));

    }

    @Test
    void size() {
        BinTree<Integer> tree = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        assertEquals(7, tree.size());
    }

    @Test
    void depth7() {
        BinTree<Integer> tree = mkTree(1,2,3,4,5,6,7); // ends up as linked list
        assertEquals(7, tree.depth());
    }

    @Test
    void depth3() {
        BinTree<Integer> tree = BinTree.build(new Integer[] {4,2,6,1,3,5,7});
        assertEquals(3, tree.depth());
    }

    @Test
    void duplicate1() {
        BinTree<Integer> tree = BinTree.empty();
        tree.insert(1);
        for (int i=0; i<10; i++) tree.insert(1);
        assertEquals(1, tree.size());
    }

    @Test
    void duplicateK() {
        final int N = 10;
        final int K = 100;
        BinTree<Integer> tree = BinTree.empty();
        for (int i=0; i<N; i++) tree.insert(i);
        for (int k=0; k<K; k++) for (int i=0; i<N; i++) tree.insert(i);
        assertEquals(N, tree.size());
        assertEquals(N, tree.depth());
    }

    @Test
    void inorder() {
        BinTree<Integer> tree = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        List<Integer> expected = Arrays.asList(1,2,3,4,5,6,7);
        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void preorder() {
        BinTree<Integer> tree = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        List<Integer> expected = Arrays.asList(4,2,1,3,6,5,7);
        List<Integer> actual = new ArrayList<>();
        tree.preorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void postorder() {
        BinTree<Integer> tree = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        List<Integer> expected = Arrays.asList(1,3,2,5,7,6,4);
        List<Integer> actual = new ArrayList<>();
        tree.postorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void reverse() {
        BinTree<Integer> tree = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        List<Integer> expected = Arrays.asList(7,6,5,4,3,2,1);
        tree.reverse();
        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void dfs() {
        BinTree<Integer> bt = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        List<Integer> exp = Arrays.asList(1,3,2,5,7,6,4); // same as post-order
        List<Integer> act = new ArrayList<>();
        bt.dfs(act::add);
        assertEquals(exp, act);
    }

    @Test
    void bfs() {
        BinTree<Integer> bt = BinTree.build(new Integer[] {1,2,3,4,5,6,7});
        List<Integer> exp = Arrays.asList(4,2,6,1,3,5,7); // same as pre-order
        List<Integer> act = new ArrayList<>();
        bt.bfs(act::add);
        assertEquals(exp, act);
    }

    @Test
    void build() {
        BinTree<Integer> bt = BinTree.build(new Integer[] {1, 2, 3});
        assertEquals(2, bt.depth());
        assertEquals(3, bt.size());

        assertEquals(Integer.valueOf(2), bt.getRoot().val);
        assertNotNull(bt.getRoot().lo);
        assertEquals(Integer.valueOf(1), bt.getRoot().lo.val);
        assertNotNull(bt.getRoot().hi);
        assertEquals(Integer.valueOf(3), bt.getRoot().hi.val);
    }

    static BinTree<Integer> mkTree(int ...items) {
        BinTree<Integer> tree = BinTree.empty();
        for (int i : items) tree.insert(i);
        return tree;
    }
}
