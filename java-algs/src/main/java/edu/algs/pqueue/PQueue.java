package edu.algs.pqueue;

public class PQueue<T> {
    private final int N;
    private final Entry<T> items[];

    private int size = 0;

    public PQueue(int n) {
        this.N = n;
        this.items = (Entry<T>[]) new Entry[N];
    }

    public void add(T item, long p) {
        if (size == N) {
            throw new IllegalStateException("PQueue overflow");
        }
        items[size] = Entry.<T>of(item, p);
        size += 1;
        rise(size-1);
    }

    public T top() {
        if (size == 0) {
            throw new IllegalStateException("PQueue underflow");
        }
        Entry<T> e = items[0];
        items[0] = items[size-1];
        items[size-1] = null;
        size -= 1;
        sink(0);
        return e.item;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("PQueue underflow");
        }
        return items[0].item;
    }

    public void set(int item, long p) {
        int i = find(item);
        if (i < 0) return;
        long pp = items[i].p;
        if (p == pp) return;
        items[i].p = p;
        if (p < pp) rise(i);
        else        sink(i);
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    void sink(int idx) {
        int i = idx;
        while (!fits(i)) {
            int l = lnode(i);
            int r = rnode(i);
            int m = minidx(l, r);
            swap(i, m);
            i = m;
        }
    }

    void rise(int idx) {
        int i = idx;
        int p = parent(i);
        while (i>0 && less(i, p)) {
            swap(i, p);
            i = p;
            p = parent(i);
        }
    }

    int find(int item) { for (int i=0; i<size; i++) if (items[i].item.equals(item)) return i; return -1; }
    int parent(int i) { return (i >= size || i <= 0) ? -1 : (i+1)/2-1; }
    int lnode(int i) { int l = (i+1)*2-1; return (i < 0 || l >= size) ? -1 : l; }
    int rnode(int i) { int r = (i+1)*2;   return (i < 0 || r >= size) ? -1 : r; }
    boolean less(int i, int j) { return items[i].p <= items[j].p; }

    int minidx(int i, int j) {
        if (i<0 && j<0) throw new IllegalArgumentException("Indices cannot be negative");
        else if (i*j < 0) return Math.max(i,j);
        else if (i>=size && j>=size) return -1;
        else if (i<size && j<size) return (less(i,j)) ? i : j;
        else return Math.min(i,j);
    }

    boolean fits(int i) {
        int l = lnode(i);
        int r = rnode(i);
        return (l<0 && r<0) ||
                (r<0 && less(i, l)) ||
                (l<0 && less(i, r)) ||
                (less(i, l) && less(i, r));
    }

    void swap(int i, int j) {
        Entry<T> ti = items[i];
        items[i] = items[j];
        items[j] = ti;
    }

    private static class Entry<T> {
        final T item;
        long p;
        private Entry(T item, long p) { this.item = item; this.p = p; }
        static <T> Entry<T> of(T item, long p) { return new Entry<T>(item, p); }
    }
}
