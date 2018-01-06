package edu.algs.pqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntPQueueTest {

    @Test
    void ordering() {
        final int n = 100;
        IntPQueue pq = new IntPQueue(n);
        assertTrue(pq.isEmpty());
        for (int i=0; i<n; i++) {
            pq.add(i, n-1-i);
        }
        assertEquals(n, pq.size());

        List<Integer> actual = new ArrayList<>(n);
        List<Integer> expected = new ArrayList<>(n);
        for (int i=0; i<n; i++) {
            expected.add(n-1-i);
            actual.add(pq.top());
        }

        assertTrue(pq.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    void bulkOrdering() {
        final int n = 10;
        final IntPQueue pq = new IntPQueue(2*n);
        for (int i=0; i<n; i++) pq.add(i, 10*n+i);
        for (int i=0; i<n; i++) pq.add(10*n+i, i);

        List<Integer> actual = new ArrayList<>(n);
        List<Integer> expected = new ArrayList<>(n);
        for (int i=0; i<n; i++) { expected.add(10*n+i); actual.add(pq.top()); }
        for (int i=0; i<n; i++) { expected.add(     i); actual.add(pq.top()); }

        assertTrue(pq.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    void setPriority() {
        final int n = 10;
        final IntPQueue pq = new IntPQueue(2*n);
        for (int i=0; i<n; i++) pq.add(i, 100+i);
        for (int i=0; i<n; i++) pq.add(100+i, 200+i);
        for (int i=0; i<n; i++) pq.set(100+i, i);

        List<Integer> actual = new ArrayList<>(n);
        List<Integer> expected = new ArrayList<>(n);
        for (int i=0; i<n; i++) { expected.add(100+i); actual.add(pq.top()); }
        for (int i=0; i<n; i++) { expected.add(    i); actual.add(pq.top()); }

        assertTrue(pq.isEmpty());
        assertEquals(expected, actual);

    }
}
