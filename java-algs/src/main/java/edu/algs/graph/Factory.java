package edu.algs.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Factory {

    public static Graph of(final int n) {
        return new Graph(n);
    }

    public static Graph matrix(final int matrix[][]) {
        if (matrix == null) {
            throw new IllegalStateException("Adjacency matrix cannot be null");
        }
        int n = matrix.length;
        final Graph graph = new Graph(n);
        for (int i=0; i<n; i++) {
            if (matrix[i].length != n) {
                throw new IllegalArgumentException("Adjacency matrix must be square");
            }
            for (int j=0; j<matrix[i].length; j++) {
                int w = matrix[i][j];
                if (i !=j && w > 0) {
                    graph.edge(i, j, w);
                }
            }
        }
        return graph;
    }

    public static Graph array(int n, int es[][]) { return array(n, es, true); }

    public static Graph array(int n, int es[][], boolean directed) { return array(n, es, directed, 0); }

    public static Graph array(int n, int es[][], boolean directed, int offset) {
        int k = directed ? 1 : 2;
        List<Edge> edges = new ArrayList<>(es.length * k);
        for (int i=0; i<es.length; i++) {
            Edge e = fetchEdge(es[i], offset);
            edges.add(e);
            if (!directed) edges.add(e.reverse());
        }
        return edges(n, edges);
    }

    private static Edge fetchEdge(int a[], int offset) {
        if (a.length > 2) {
            return new Edge(a[0]-1, a[1]-1, a[2]);
        } else {
            return new Edge(a[0]-1, a[1]-1);
        }
    }

    public static Graph edges(int n, List<Edge> edges) { return edges(n, edges, true); }

    public static Graph edges(int n, List<Edge> edges, boolean directed) {
        final Graph graph = new Graph(n);
        edges.forEach(graph::edge);
        if (!directed) {
            edges.stream().map(Edge::reverse).forEach(graph::edge);
        }
        return graph;
    }

    public static Graph string(String str, boolean directed, int offset, boolean minlen) {
        InputStream is = new ByteArrayInputStream(str.getBytes(Charset.defaultCharset()));
        return stream(is, directed, offset, minlen);
    }

    public static Graph stream(InputStream is) { return stream(is, true, 0, false); }

    public static Graph stream(InputStream is, boolean directed) { return stream(is, directed, 0, false); }

    public static Graph stream(InputStream is, boolean directed, int offset) { return stream(is, directed, offset, false); }

    public static Graph stream(InputStream is, boolean directed, int offset, boolean minlen) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader(reader, directed, offset, minlen);
    }

    public static Graph reader(BufferedReader reader, boolean directed, int offset, boolean minlen) {
        try {
            Graph.Size size = parseHeader(reader.readLine());
            List<Edge> edges = new ArrayList<>(size.edges);
            if (minlen) {
                int unique[][] = new int[size.nodes][size.nodes];
                for (int i = 0; i < size.edges; i++) {
                    Edge e = parseEdge(reader.readLine(), offset);
                    int u = e.source;
                    int v = e.target;
                    int w = e.weight;
                    int oldw = unique[u][v];
                    int neww = Math.min(w, Math.max(0, oldw));
                    if (directed) {
                        unique[u][v] = neww;
                    } else {
                        oldw = Math.min(
                                Math.max(0, unique[u][v]),
                                Math.max(0, unique[v][u]));
                        neww = (oldw == 0) ? w : Math.min(w, oldw);
                        unique[u][v] = neww;
                        unique[v][u] = neww;
                    }
                }
                for (int i=0; i < size.nodes; i++) {
                    for (int j=0; j < size.nodes; j++) {
                        int w = unique[i][j];
                        if (w > 0) edges.add(new Edge(i, j, w));
                    }
                }
                return edges(size.nodes, edges);
            } else {
                for (int i = 0; i < size.edges; i++) {
                    Edge edge = parseEdge(reader.readLine(), offset);
                    edges.add(edge);
                }
                return edges(size.nodes, edges, directed);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to parse graph", e);
        }
    }

    private static Graph.Size parseHeader(String line) {
        StringTokenizer st = new StringTokenizer(line);
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        return new Graph.Size(n, m);
    }

    private static Edge parseEdge(String line, int offset) {
        StringTokenizer st = new StringTokenizer(line);
        int u = Integer.parseInt(st.nextToken()) - offset;
        int v = Integer.parseInt(st.nextToken()) - offset;
        if (st.hasMoreTokens()) {
            int w = Integer.parseInt(st.nextToken());
            return new Edge(u, v, w);
        } else {
            return new Edge(u, v);
        }
    }
}
