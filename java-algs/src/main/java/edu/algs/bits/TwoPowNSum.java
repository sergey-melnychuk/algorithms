package edu.algs.bits;

import java.util.BitSet;
import java.util.function.Consumer;

public class TwoPowNSum {
    private final BitSet bs = new BitSet();

    public void add2pown(int n) {
        while (bs.get(n)) { bs.clear(n); n += 1; } // find first 0 bit (manually handling carrying)
        bs.set(n);
    }

    public void add2pownk(int n, int k) {
        bits(k, b -> add2pown(n + b));
    }

    static void bits(int k, Consumer<Integer> bit) {
        int n = 0;
        while (k>0) {
            if ((k & 1)>0) bit.accept(n);
            k = k >> 1;
            n += 1;
        }
    }

    @Override
    public String toString() {
        if (bs.isEmpty()) return "";
        final int N = bs.length();
        char cs[] = new char[N];
        for (int i=0; i<N; i++) cs[i] = '0';
        for (int i=bs.nextSetBit(0); i>=0; i=bs.nextSetBit(i+1)) {
            if (i == Integer.MAX_VALUE) break; // or (i+1) would overflow
            cs[N-1-i] = '1';
        }
        return new String(cs);
    }
}
