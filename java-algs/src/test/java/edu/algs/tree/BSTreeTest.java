package edu.algs.tree;

import edu.algs.array.Array;
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
        Node<Integer> T = Node.value(null, 3);
        Node<Integer> L = Node.value(T, 2);
        Node<Integer> LL = Node.value(L, 1);
        T.lo = L;
        L.lo = LL;
        LL.touch();
        L.touch();

        BSTree<Integer> bt = new BSTree<>();
        bt.setRoot(T);

        Node<Integer> R = bt.moveL(T);
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
        Node<Integer> T = Node.value(null, 1);
        Node<Integer> H = Node.value(T, 2);
        Node<Integer> HH = Node.value(H, 3);
        T.hi = H;
        H.hi = HH;
        HH.touch();
        H.touch();

        BSTree<Integer> bt = new BSTree<>();
        bt.setRoot(T);

        Node<Integer> R = bt.moveH(T);
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
        Node<Integer> T = Node.value(null, 3);
        Node<Integer> L = Node.value(T, 1);
        Node<Integer> H = Node.value(L, 2);
        T.lo = L;
        L.hi = H;
        L.touch();
        H.touch();

        BSTree<Integer> bt = new BSTree<>();
        bt.setRoot(T);

        Node<Integer> R = bt.moveLH(T);

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
        1          2
         \        / \
          3      1  3
         /
        2
     */
    @Test
    void moveHL() {
        Node<Integer> T = Node.value(null, 1);
        Node<Integer> H = Node.value(T, 3);
        Node<Integer> L = Node.value(H, 2);
        T.hi = H;
        H.lo = L;
        H.touch();
        T.touch();

        BSTree<Integer> bt = new BSTree<>();
        bt.setRoot(T);

        Node<Integer> R = bt.moveHL(T);
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

    @Test
    void ordered2() { ordered(2, 0); }

    @Test
    void ordered3() { ordered(3, 0); }

    @Test
    void ordered5() { ordered(5, 1); }

    @Test
    void ordered10() { ordered(10, 5); }

    @Test
    void ordered15() { ordered(15, 7); }

    @Test
    void ordered20() { ordered(20, 8); }

    @Test
    void random5() { random(5, 1); }

    @Test
    void random10() { random(10, 5); }

    @Test
    void random15() { random(15, 10); }

    @Test
    void random20() { random(20, 13); }

    private void ordered(int d, int relaxation) { ordered(d, relaxation, false); }

    private void ordered(int d, int relaxation, boolean debug) {
        int n = (1 << d) - 1;
        BSTree<Integer> tree = new BSTree<>();
        for (int i=0; i<n; i++) tree.insert(i);
        if (debug) System.out.println(tree.shape(3));
        assertEquals(n, tree.size());
        assertEquals(d + relaxation, tree.depth(), "depth");
    }

    private void random(int d, int relaxation) { random(d, relaxation, false); }

    private void random(int d, int relaxation, boolean debug) {
        int n = (1 << d) - 1;
        BSTree<Integer> tree = new BSTree<>();
        int a[] = mkRandom(n);
        for (int i=0; i<n; i++) {
            tree.insert(a[i]);
            if (debug) {
                System.out.println("insert(" + a[i] + ")");
                System.out.println(tree.shape(3));
            }
        }
        assertEquals(n, tree.size());
        assertEquals(d + relaxation, tree.depth(), "depth");

        List<Integer> actual = new ArrayList<>();
        tree.inorder(actual::add);

        Arrays.sort(a);
        int b[] = new int[n];
        for (int i=0; i<n; i++) b[i] = actual.get(i);

        assertArrayEquals(a, b);
    }

    private static int[] mkOrdered(int n) {
        int a[] = new int[n];
        for (int i=0; i<n; i++) a[i] = i;
        return a;
    }

    private static int[] mkRandom(int n) {
        Random random = new Random(42L);
        int a[] = mkOrdered(n);
        for (int i=0; i<n-2; i++) {
            int j = i + 1 + random.nextInt(n-2-i);
            Array.swap(a, i, j);
        }
        return a;
    }
}
