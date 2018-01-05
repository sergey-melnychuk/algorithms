package edu.algs.dset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DSetTest {

    @Test
    void indexNegative() {
        final DSet dset = new DSet(3);
        Throwable t = assertThrows(IllegalArgumentException.class, () -> dset.find(-1));
        assertEquals("Invalid node index: -1", t.getMessage());
    }

    @Test
    void indexOverflow() {
        final DSet dset = new DSet(3);
        Throwable t = assertThrows(IllegalArgumentException.class, () -> dset.find(3));
        assertEquals("Invalid node index: 3", t.getMessage());
    }

    @Test
    void initialState() {
        final int n = 10;
        DSet dset = new DSet(n);
        for (int i=0; i<n; i++) {
            assertEquals(i, dset.find(i));
        }
    }

    @Test
    void union2() {
        DSet dset = new DSet(2);
        dset.union(1, 0);
        assertEquals(0, dset.find(1));
    }

    @Test
    void balancedUnion3() {
        DSet dset = new DSet(3);
        dset.union(1, 2);
        dset.union(0, 1);
        assertEquals(2, dset.find(0));
    }

    @Test
    void unionFull() {
        final int n = 1024;
        DSet dset = new DSet(1024);
        for (int i=1; i<n; i++) dset.union(i, i-1);
        for (int i=0; i<n; i++) assertEquals(0, dset.find(i));
    }

}
