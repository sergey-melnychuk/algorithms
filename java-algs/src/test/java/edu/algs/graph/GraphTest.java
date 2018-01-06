package edu.algs.graph;

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

        List<Edge> expected = Arrays.asList(
                new Edge(0, 2, 3),
                new Edge(2, 1, 4),
                new Edge(2, 3, 5)
        );

        Graph g = new Graph(n);
        for (int i=0; i<edges.length; i++) {
            int s = edges[i][0]-1;
            int t = edges[i][1]-1;
            int w = edges[i][2];
            g.edge(s, t, w, false);
        }

        List<Edge> mst = g.kruskal();
        assertEquals(expected, mst);
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
        assertEquals("Can't run BFS on weighted graph", t.getMessage());
    }

    @Test
    void dijkstra3() {
        String str = "4 4\n1 2 24\n1 4 20\n3 1 3\n4 3 12\n";
        InputStream is = new ByteArrayInputStream(str.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false, 1, false);
        long expected[] = {0, 24, 3, 15};
        assertArrayEquals(expected, g.dijkstra(0));
    }
}
