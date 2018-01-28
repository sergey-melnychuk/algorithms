package edu.algs.custom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuccessorSetTest {

    @Test
    void one() {
        SuccessorSet set = set(1);
        assertEquals(0, set.next(0));
    }

    @Test
    void empty() {
        SuccessorSet set = set(1);
        set.remove(0);
        assertEquals(-1, set.next(0));
    }

    @Test
    void full() {
        final int n = 10;
        SuccessorSet set = set(n);
        for (int i=0; i<n; i++) {
            assertEquals(i, set.next(i));
        }
    }

    @Test
    void size2() {
        SuccessorSet set = set(2);
        set.remove(0);
        assertEquals(1, set.next(0));
        assertEquals(1, set.next(1));
    }

    @Test
    void size3() {
        SuccessorSet set = set(3);
        set.remove(0);
        set.remove(1);
        assertEquals(2, set.next(0));
        assertEquals(2, set.next(1));
        assertEquals(2, set.next(2));
    }

    @Test
    void size5() {
        SuccessorSet set = set(5);
        set.remove(0);
        set.remove(1);
        set.remove(2);
        assertEquals(3, set.next(0));
        assertEquals(3, set.next(1));
        assertEquals(3, set.next(2));
        assertEquals(3, set.next(3));
        assertEquals(4, set.next(4));
    }

    @Test
    void asc10() { asc(10); }

    @Test
    void asc100() { asc(100); }

    @Test
    void desc10() { desc(10); }

    private void asc(final int n) {
        SuccessorSet set = set(n);
        for (int i=0; i<n; i++) {
            set.remove(i);
            int exp = (i < n-1) ? i+1 : -1;
            for (int j=0; j<=i; j++) {
                int act = set.next(j);
                assertEquals(exp, act, String.format("set.remove(0..%d).next(%d) expected %d but was %d", i, j, exp, act));
            }
        }
    }

    private void desc(final int n) {
        SuccessorSet set = set(n);
        for (int i=n-1; i>=0; i--) {
            set.remove(i);
            int exp = -1;
            for (int j=i; j<n; j++) {
                int act = set.next(j);
                assertEquals(exp, act, String.format("set.remove(%d..%d).next(%d) expected %d but was %d", i, n-1, i, exp, act));
            }
        }
    }

    private SuccessorSet set(int n) { return SuccessorSetImpl.create(n); }
}
