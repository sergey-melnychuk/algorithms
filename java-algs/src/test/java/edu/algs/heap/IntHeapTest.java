package edu.algs.heap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IntHeapTest {

    @Test
    void emptyUnderflow() {
        final IntHeap heap = new IntHeap(3);
        Throwable t = assertThrows(IllegalStateException.class, heap::top);
        assertEquals("Get from empty heap", t.getMessage());
    }

    @Test
    void fullOverflow() {
        final IntHeap heap = new IntHeap(3);
        for (int i=0; i<3; i++) heap.add(i);
        Throwable t = assertThrows(IllegalStateException.class, () -> heap.add(100));
        assertEquals("Heap capacity overflow", t.getMessage());
    }

    @Test
    void lessAsc() {
        final IntHeap heap = new IntHeap(3);
        heap.add(0);
        heap.add(1);
        heap.add(2);
        assertTrue(heap.less(0, 1));
        assertTrue(heap.less(1, 2));
        assertEquals(Arrays.asList(0, 1, 2), items(heap));
    }

    @Test
    void lessDesc() {
        final IntHeap heap = new IntHeap(3, false);
        heap.add(2);
        heap.add(1);
        heap.add(0);
        assertTrue(heap.less(0, 1));
        assertTrue(heap.less(1, 2));
        assertEquals(Arrays.asList(2, 1, 0), items(heap));
    }

    @Test
    void swap() {
        final IntHeap heap = new IntHeap(3);
        for (int i=0; i<3; i++) heap.add(0);
        int raw[] = heap.raw();
        for (int i=0; i<3; i++) raw[i] = i;
        assertEquals(Arrays.asList(0, 1, 2), items(heap));

        heap.swap(0, 1);
        assertEquals(Arrays.asList(1, 0, 2), items(heap));
    }

    @Test
    void rise3() {
        final IntHeap heap = new IntHeap(3);
        assertTrue(items(heap).isEmpty());
        heap.add(2);
        assertEquals(Collections.singletonList(2), items(heap));
        heap.add(1);
        assertEquals(Arrays.asList(1, 2), items(heap));
        heap.add(0);
        assertEquals(Arrays.asList(0, 2, 1), items(heap));
    }

    @Test
    void sink3() {
        final IntHeap heap = new IntHeap(3);
        heap.add(2);
        heap.add(1);
        heap.add(0);
        assertEquals(Arrays.asList(0, 2, 1), items(heap));

        assertEquals(0, heap.top());
        assertEquals(Arrays.asList(1, 2), items(heap));

        assertEquals(1, heap.top());
        assertEquals(Collections.singletonList(2), items(heap));

        assertEquals(2, heap.top());
        assertTrue(items(heap).isEmpty());
    }

    @Test
    void ordering3() { ordering(3); }

    @Test
    void ordering7() { ordering(7); }

    @Test
    void ordering1024() { ordering(1024); }

    private void ordering(final int n) {
        final IntHeap heap = new IntHeap(n);
        for (int i=0; i<n; i++) heap.add(n-1-i);
        for (int i=0; i<n; i++) assertEquals(i, heap.top());
        assertTrue(heap.isEmpty());
    }

    private List<Integer> items(IntHeap heap) {
        return Arrays.stream(heap.raw()).boxed().limit(heap.size()).collect(Collectors.toList());
    }
}
