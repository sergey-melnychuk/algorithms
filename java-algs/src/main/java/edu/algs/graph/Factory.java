package edu.algs.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static Graph array(int n, int a[][]) { return array(n, a, 0); }

    public static Graph array(int n, int a[][], int offset) {
        final Graph g = of(n);
        for (int i=0; i<a.length; i++) {
            int u = a[i][0] - offset;
            int v = a[i][1] - offset;
            int w = a[i][2];
            g.edge(u, v, w);
        }
        return g;
    }

    public static Graph edges(List<Edge> edges) { return edges(edges, true, 0); }

    public static Graph edges(List<Edge> edges, boolean directed, int offset) {
        int n = 0;
        for (Edge e : edges) {
            n = Math.max(n, Math.max(e.source, e.target));
        }
        final Graph graph = new Graph(n + offset + 1);
        edges.forEach(graph::edge);
        if (!directed) {
            edges.stream().map(Edge::reverse).forEach(graph::edge);
        }
        return graph;
    }

    public static Graph stream(InputStream is) { return stream(is, true, 0, false); }

    public static Graph stream(InputStream is, boolean directed) { return stream(is, directed, 0, false); }

    public static Graph stream(InputStream is, boolean directed, int offset, boolean dedup) {
        try(final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            Graph.Size size = parseHeader(reader.readLine());
            List<Edge> edges = new ArrayList<>(size.edges);
            if (dedup) {
                int unique[][] = new int[size.edges][3];
                for (int i = 0; i < size.edges; i++) {
                    int[] edge = parseEdge(reader.readLine());
                    int u = edge[0] - offset;
                    int v = edge[1] - offset;
                    int w = 1;
                    unique[u][v] = w;
                    if (edge.length > 2) w = edge[2];
                }
                for (int i = 0; i < size.edges; i++) {
                    int u = unique[i][0];
                    int v = unique[i][1];
                    int w = unique[i][2];
                    edges.add(new Edge(u, v, w));
                }
                return edges(edges, directed, offset);
            } else {
                for (int i = 0; i < size.edges; i++) {
                    int[] edge = parseEdge(reader.readLine());
                    int u = edge[0] - offset;
                    int v = edge[1] - offset;
                    if (edge.length > 2) {
                        int w = edge[2];
                        edges.add(new Edge(u, v, w));
                    } else {
                        edges.add(new Edge(u, v));
                    }
                }
                return edges(edges, directed, offset);
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

    private static int[] parseEdge(String line) {
        StringTokenizer st = new StringTokenizer(line);
        int u = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        if (st.hasMoreTokens()) {
            int w = Integer.parseInt(st.nextToken());
            return new int[] {u, v, w};
        } else {
            return new int[] {u, v};
        }
    }
}
