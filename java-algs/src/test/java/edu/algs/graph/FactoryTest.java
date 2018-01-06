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

    @Test
    void streamUndir() {
        String s =
                "4 6\n" +
                "0 1 5\n" +
                "0 2 3\n" +
                "1 3 7\n" +
                "2 1 4\n" +
                "2 3 5\n" +
                "3 0 6\n";
        InputStream is = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false);
        String expected =
                "4 12\n" +
                "0 1 5\n" +
                "0 2 3\n" +
                "0 3 6\n" +
                "1 3 7\n" +
                "1 0 5\n" +
                "1 2 4\n" +
                "2 1 4\n" +
                "2 3 5\n" +
                "2 0 3\n" +
                "3 0 6\n" +
                "3 1 7\n" +
                "3 2 5\n";
        assertEquals(expected, g.toString());
    }

    @Test
    void streamUndirOffset() {
        String s =
                "4 6\n" +
                "10 11 5\n" +
                "10 12 3\n" +
                "11 13 7\n" +
                "12 11 4\n" +
                "12 13 5\n" +
                "13 10 6\n";
        InputStream is = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false, 10);
        String expected =
                "4 12\n" +
                "0 1 5\n" +
                "0 2 3\n" +
                "0 3 6\n" +
                "1 3 7\n" +
                "1 0 5\n" +
                "1 2 4\n" +
                "2 1 4\n" +
                "2 3 5\n" +
                "2 0 3\n" +
                "3 0 6\n" +
                "3 1 7\n" +
                "3 2 5\n";
        assertEquals(expected, g.toString());
    }

    @Test
    void streamUndirOffsetMinlen() {
        String s =
                "4 18\n" +
                "10 11 5\n" +
                "10 11 50\n" +
                "10 11 500\n" +
                "10 12 3\n" +
                "10 12 30\n" +
                "10 12 300\n" +
                "11 13 7\n" +
                "11 13 70\n" +
                "11 13 700\n" +
                "12 11 4\n" +
                "12 11 40\n" +
                "12 11 400\n" +
                "12 13 5\n" +
                "12 13 50\n" +
                "12 13 500\n" +
                "13 10 6\n" +
                "13 10 60\n" +
                "13 10 600\n";
        InputStream is = new ByteArrayInputStream(s.getBytes(Charset.defaultCharset()));
        Graph g = Factory.stream(is, false, 10, true);
        String expected =
                "4 12\n" +
                "0 1 5\n" +
                "0 2 3\n" +
                "0 3 6\n" +
                "1 0 5\n" +
                "1 2 4\n" +
                "1 3 7\n" +
                "2 0 3\n" +
                "2 1 4\n" +
                "2 3 5\n" +
                "3 0 6\n" +
                "3 1 7\n" +
                "3 2 5\n";
        assertEquals(expected, g.toString());
    }
}
