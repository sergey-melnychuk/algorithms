package edu.algs.graph;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
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
    @Disabled //FIXME
    void prim4HR() {
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

    @Test
    void bfs3() {
        final int n = 3;
        List<Edge> es = Arrays.asList(
                new Edge(0, 1),
                new Edge(1, 2),
                new Edge(0, 2)
        );
        Graph g = Factory.edges(n, es, true);
        long expected[] = {0, 1, 1};
        assertArrayEquals(expected, g.bfs(0));
    }

    /*
    A----(3)----B
    |           |
    \-(1)-C-(1)-/
    see: see https://stackoverflow.com/a/30409594
     */
    @Test
    void weightedBfsThrows() {
        Graph g = Factory.edges(2, Arrays.asList(new Edge(1, 1, 2)), false);
        Throwable t = assertThrows(UnsupportedOperationException.class, () -> g.bfs(0));
        assertEquals("Attempt to run BFS on weighted graph (leads to incorrect result)", t.getMessage());
    }

    @Test
    void dijkstra3() {
        String str = "4 4\n1 2 24\n1 4 20\n3 1 3\n4 3 12\n";
        InputStream is = new ByteArrayInputStream(str.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false, 1, false);
        long expected[] = {0, 24, 3, 15};
        assertArrayEquals(expected, g.dijkstra(0));
    }

    private static final String G =
            "5 6\n" +
            "1 2 3\n" +
            "1 3 4\n" +
            "4 2 6\n" +
            "5 2 2\n" +
            "2 3 5\n" +
            "3 5 7\n";

    @Test
    void mst() {
        InputStream is = new ByteArrayInputStream(G.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false, 1, false);
        List<Edge> k = g.kruskal();
        k.sort(Edge.ASC);
        int n = g.size().nodes;
        for (int i=0; i<n; i++) {
            List<Edge> p = g.prim(i);
            p.sort(Edge.ASC);
            assertEquals(k, p, "mismatch for prim's starting node " + i);
        }
    }
}
