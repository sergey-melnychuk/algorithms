package edu.algs.graph;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryTest {

    @Test
    void of() {
        Graph g = Factory.of(3);
        Graph.Size size = g.size();
        assertEquals(0, size.edges);
        assertEquals(3, size.nodes);
        assertTrue(g.getEdges().isEmpty());
    }

    @Test
    void matrix() {
        int [][] matrix = {
                { 0,  5,  3, -1},
                {-1,  0, -1,  7},
                {-1,  4,  0,  5},
                { 6, -1, -1,  0}
        };

        Graph g = Factory.matrix(matrix);

        Graph.Size size = g.size();
        assertEquals(4, size.nodes);
        assertEquals(6, size.edges);

        List<Edge> expected = Arrays.asList(
                new Edge(0, 2, 3),
                new Edge(2, 1, 4),
                new Edge(0, 1, 5),
                new Edge(2, 3, 5),
                new Edge(3, 0, 6),
                new Edge(1, 3, 7)
        );
        assertEquals(expected, g.getEdges(true));
    }

    @Test
    void edges() {
        int n = 4;
        List<Edge> expected = Arrays.asList(
                new Edge(0, 2, 3),
                new Edge(2, 1, 4),
                new Edge(0, 1, 5),
                new Edge(2, 3, 5),
                new Edge(3, 0, 6),
                new Edge(1, 3, 7)
        );
        Graph g = Factory.edges(n, expected, true);
        assertEquals(expected, g.getEdges(true));
    }

    @Test
    void stream() {
        String s = "4 6\n0 1 5\n0 2 3\n1 3 7\n2 1 4\n2 3 5\n3 0 6\n";
        InputStream is = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is);
        assertEquals(s, g.toString());
    }
}
