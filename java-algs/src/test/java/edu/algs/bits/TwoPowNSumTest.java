package edu.algs.bits;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwoPowNSumTest {

    @Test
    void bits() {
        //                           -9-76---2--
        int x = Integer.parseInt("01011000100", 2);
        List<Integer> expected = Arrays.asList(2, 6, 7, 9);
        final List<Integer> actual = new ArrayList<>();
        TwoPowNSum.bits(x, actual::add);
        assertEquals(expected, actual);
    }

    @Test
    void empty() {
        TwoPowNSum tpns = new TwoPowNSum();
        assertEquals("", tpns.toString());
    }

    @Test
    void count4() {
        TwoPowNSum tpns = new TwoPowNSum();
        tpns.add2pown(0);
        assertEquals("1", tpns.toString());
        tpns.add2pown(0);
        assertEquals("10", tpns.toString());
        tpns.add2pown(0);
        assertEquals("11", tpns.toString());
        tpns.add2pown(0);
        assertEquals("100", tpns.toString());
    }

    @Test
    void count1024() {
        TwoPowNSum tpns = new TwoPowNSum();
        for (int i=0; i<1024; i++) {
            tpns.add2pown(0);
            assertEquals(Integer.toBinaryString(i+1), tpns.toString());
        }
    }

    @Test
    void kcount1024() {
        TwoPowNSum tpns = new TwoPowNSum();
        for (long i=0; i<1024; i++) {
            tpns.add2pownk(10, 10);
            assertEquals(Long.toBinaryString(10*(i+1)*1024), tpns.toString());
        }
    }

    @Test
    void sum() {
        TwoPowNSum tpns = new TwoPowNSum();
        tpns.add2pownk(1, 4);
        tpns.add2pownk(0, 4);
        tpns.add2pownk(2, 6);
        tpns.add2pownk(3, 4);
        assertEquals(Integer.toBinaryString(68), tpns.toString()    );
    }

    @Test
    void carry() {
        TwoPowNSum tpns = new TwoPowNSum();
        for (int i=0; i<10; i++) tpns.add2pown(i);
        assertEquals( "1111111111", tpns.toString());
        tpns.add2pown(0);
        assertEquals("10000000000", tpns.toString());
    }

    @Test
    void sumk() {
        int k = 19;
        int n = 27;
        TwoPowNSum tpns = new TwoPowNSum();
        tpns.add2pownk(n, k);
        assertEquals(Integer.toBinaryString((1<<n)*k), tpns.toString());
    }

    @Test
    void sumk128() {
        int n = 128;
        int k = 9;
        TwoPowNSum s = new TwoPowNSum();
        s.add2pownk(n, k);
        assertEquals(Integer.toBinaryString(k) + zeros(n), s.toString());
    }

    private String zeros(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<n; i++) sb.append('0');
        return sb.toString();
    }
}
