package edu.algs.graph;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphTest {

    @Test
    void kruskal4() {
        final int n = 4;
        int edges[][] = {
            {1, 2, 5},
            {1, 3, 3},
            {4, 1, 6},
            {2, 4, 7},
            {3, 2, 4},
            {3, 4, 5}
        };
        Graph g = Factory.array(n, edges, false, 1);

        List<Edge> expected = Arrays.asList(
                new Edge(0, 2, 3),
                new Edge(1, 2, 4),
                new Edge(2, 3, 5)
        );

        List<Edge> mst = g.kruskal();
        assertEquals(expected, mst);
    }

    @Test
    void prim4() {
        final int n = 4;
        int edges[][] = {
            {1, 2, 5},
            {1, 3, 3},
            {4, 1, 6},
            {2, 4, 7},
            {3, 2, 4},
            {3, 4, 5}
        };
        Graph g = Factory.array(n, edges, false, 1);

        List<Edge> expected = Arrays.asList(
                new Edge(0, 2, 3),
                new Edge(1, 2, 4),
                new Edge(2, 3, 5)
        );

        List<Edge> mst = g.prim(2);
        mst.sort(Edge.ASC);
        assertEquals(expected, mst);
    }

    @Test
    void prim4HR() {
        /*
          -----------------100*-----------------
         |                                      |
        (0)----1*---(1)----150----(2)----99*---(3)
         |                         |
          -----------200------------
         */
        String s = "4 5\n" +
                "1 2 1\n" +
                "3 2 150\n" +
                "4 3 99\n" +
                "1 4 100\n" +
                "3 1 200\n";
        Graph g = Factory.string(s, false, 1, false);

        List<Edge> expected = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(2, 3, 99),
                new Edge(0, 3, 100)
        );

        assertEquals(expected, g.prim(3));
    }

    /*
    0------1-------------2-------------3
           |             |             |
           |             |             |
           4------5      8------10     12------14
           |             |             |
           |             |             |
           6------7      9------11     13------15
     */
    @Test
    void bfs() {
        final String s = "16 15\n" +
                "0 1 1\n" +
                "1 2 1\n" +
                "1 4 1\n" +
                "2 3 1\n" +
                "2 8 1\n" +
                "3 12 1\n" +
                "4 5 1\n" +
                "4 6 1\n" +
                "6 7 1\n" +
                "8 9 1\n" +
                "8 10 1\n" +
                "9 11 1\n" +
                "12 13 1\n" +
                "12 14 1\n" +
                "13 15 1\n";
        Graph g = Factory.string(s);

        List<Integer> expNodes = Arrays.asList(0, 1, 2, 4, 3, 8, 5, 6, 12, 9, 10, 7, 13, 14, 11, 15);
        final List<Integer> actNodes = new ArrayList<>();
        g.bfs(0, actNodes::add, null);
        assertEquals(expNodes, actNodes);

        List<Edge> expEdges = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(1, 2, 1),
                new Edge(1, 4, 1),
                new Edge(2, 3, 1),
                new Edge(2, 8, 1),
                new Edge(4, 5, 1),
                new Edge(4, 6, 1),
                new Edge(3, 12, 1),
                new Edge(8, 9, 1),
                new Edge(8, 10, 1),
                new Edge(6, 7, 1),
                new Edge(12, 13, 1),
                new Edge(12, 14, 1),
                new Edge(9, 11, 1),
                new Edge(13, 15, 1)
        );

        final List<Edge> actEdges = new ArrayList<>();
        g.bfs(0, null, actEdges::add);
        assertEquals(expEdges, actEdges);
    }

    /*
    0------1-------------2-------------3
           |             |             |
           |             |             |
           4------5      8------10     12------14
           |             |             |
           |             |             |
           6------7      9------11     13------15
     */
    @Test
    void dfs() {
        final String s = "16 15\n" +
                "0 1 1\n" +
                "1 2 1\n" +
                "1 4 1\n" +
                "2 3 1\n" +
                "2 8 1\n" +
                "3 12 1\n" +
                "4 5 1\n" +
                "4 6 1\n" +
                "6 7 1\n" +
                "8 9 1\n" +
                "8 10 1\n" +
                "9 11 1\n" +
                "12 13 1\n" +
                "12 14 1\n" +
                "13 15 1\n";
        Graph g = Factory.string(s);

        List<Integer> expNodes = Arrays.asList(15, 13, 14, 12, 3, 11, 9, 10, 8, 2, 5, 7, 6, 4, 1, 0);
        final List<Integer> actNodes = new ArrayList<>();
        g.dfs(0, actNodes::add, null);
        assertEquals(expNodes, actNodes);

        List<Edge> expEdges = Arrays.asList(
                new Edge(13, 15, 1),
                new Edge(12, 13, 1),
                new Edge(12, 14, 1),
                new Edge( 3, 12, 1),
                new Edge( 2,  3, 1),
                new Edge( 9, 11, 1),
                new Edge( 8,  9, 1),
                new Edge( 8, 10, 1),
                new Edge( 2,  8, 1),
                new Edge( 1,  2, 1),
                new Edge( 4,  5, 1),
                new Edge( 6,  7, 1),
                new Edge( 4,  6, 1),
                new Edge( 1,  4, 1),
                new Edge( 0,  1, 1)
        );

        final List<Edge> actEdges = new ArrayList<>();
        g.dfs(0, null, actEdges::add);
        assertEquals(expEdges, actEdges);
    }

    @Test
    void hops3() {
        final int n = 3;
        List<Edge> es = Arrays.asList(
                new Edge(0, 1),
                new Edge(1, 2),
                new Edge(0, 2)
        );
        Graph g = Factory.edges(n, es, true);
        int expected[] = {0, 1, 1};
        assertArrayEquals(expected, g.hops(0));
    }

    /*
    A----(3)----B
    |           |
    \-(1)-C-(1)-/
    see: see https://stackoverflow.com/a/30409594
     */
    @Test
    void hopsIgnoreWeights() {
        List<Edge> es = Arrays.asList(
                new Edge(0, 1, 1),
                new Edge(0, 2, 1),
                new Edge(0, 2, 3));
        Graph g = Factory.edges(3, es, false);
        assertArrayEquals(new int [] {0, 1, 1}, g.hops(0));
    }

    @Test
    void dijkstra3() {
        String str = "4 4\n1 2 24\n1 4 20\n3 1 3\n4 3 12\n";
        InputStream is = new ByteArrayInputStream(str.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false, 1, false);
        long expected[] = {0, 24, 3, 15};
        assertArrayEquals(expected, g.dijkstra(0));
    }

    @Test
    void mst() {
        final String str =
                "5 6\n" +
                "1 2 3\n" +
                "1 3 4\n" +
                "4 2 6\n" +
                "5 2 2\n" +
                "2 3 5\n" +
                "3 5 7\n";
        Graph g = Factory.string(str, false, 1, false);
        List<Edge> k = g.kruskal();
        k.sort(Edge.ASC);
        int n = g.size().nodes;
        for (int i=0; i<n; i++) {
            List<Edge> p = g.prim(i);
            p.sort(Edge.ASC);
            assertEquals(k, p, "mismatch for prim's starting node " + i);
        }
    }

    @Test
    void sumpaths5() {
        String str = "5 4\n" +
                "1 2 8\n" +
                "2 3 2\n" +
                "2 4 4\n" +
                "4 5 1\n";
        Graph g = Factory.string(str, false, 1);
        assertEquals(68, g.sumpaths());
    }

    @Test
    void sumpaths19() {
        String str = "19 36\n" +
                "1 9 131072\n" +
                "1 13 8192\n" +
                "1 14 1024\n" +
                "2 4 128\n" +
                "2 10 2048\n" +
                "3 4 16\n" +
                "3 19 4\n" +
                "4 2 128\n" +
                "4 3 16\n" +
                "4 9 1\n" +
                "5 17 512\n" +
                "5 18 16384\n" +
                "6 7 256\n" +
                "6 14 8\n" +
                "7 6 256\n" +
                "8 9 32768\n" +
                "9 1 131072\n" +
                "9 4 1\n" +
                "9 8 32768\n" +
                "10 2 2048\n" +
                "11 12 2\n" +
                "11 18 64\n" +
                "12 11 2\n" +
                "13 1 8192\n" +
                "14 1 1024\n" +
                "14 6 8\n" +
                "15 18 4096\n" +
                "15 19 32\n" +
                "16 18 2097152\n" +
                "17 5 512\n" +
                "18 5 16384\n" +
                "18 11 64\n" +
                "18 15 4096\n" +
                "18 16 2097152\n" +
                "19 3 4\n" +
                "19 15 32";
        Graph g = Factory.string(str, false, 1);
        assertEquals(48648840, g.sumpaths());
    }

}
