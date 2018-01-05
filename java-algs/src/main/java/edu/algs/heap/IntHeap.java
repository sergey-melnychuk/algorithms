package edu.algs.heap;

import edu.algs.array.Array;

import java.util.function.BiPredicate;

public class IntHeap {
    private final int N;
    private final int items[];
    private final BiPredicate<Integer, Integer> less;
    private int size = 0;

    public IntHeap(int nrOfItems) { this(nrOfItems, true); }

    public IntHeap(int nrOfItems, final boolean min) {
        this.N = nrOfItems;
        this.items = new int[N];
        this.less = (Integer x, Integer y) -> (min && (x <= y)) || (!min && (x >= y));
    }

    public int top() {
        if (size == 0) {
            throw new IllegalStateException("Get from empty heap");
        }
        int top = items[0];
        items[0] = items[size-1];
        size -= 1;
        sink();
        return top;
    }

    public void add(int item) {
        if (size == N) {
            throw new IllegalStateException("Heap capacity overflow");
        }
        items[size] = item;
        size += 1;
        rise();
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    private void rise() {
        if (size <= 1) return;
        int i = size-1;
        int p = (i+1)/2-1;
        while (i>0 && less(i, p)) {
            swap(i, p);
            i = p;
            p = (i+1)/2-1;
        }
    }

    private void sink() {
        int i = 0;
        while (!fits(i)) {
            int l = 2*(i+1)-1;
            int r = l+1;
            int m = minidx(l, r);
            swap(i, m);
            i = m;
        }
    }

    private int minidx(int i, int j) { if (less(i, j)) return i; else return j; }

    // Check if value with specified index requires sinking into deeper layer of the heap
    private boolean fits(int i) {
        if (i >= size-1) return true; // leaf node, nowhere to sink
        int l = 2*(i+1)-1;
        int r = l+1;
        return (l < size) && (r < size) && less(i, l) && less(i, r)
                || (l >= size) && (r >= size)
                || (l == size-1) && less(i, l);
    }

    // Package-level access for following internal methods required for proper unit-test coverage

    int[] raw() { return items; }

    void swap(int i, int j) { Array.swap(items, i, j); }

    boolean less(int i, int j) { return less.test(items[i], items[j]); }
}
