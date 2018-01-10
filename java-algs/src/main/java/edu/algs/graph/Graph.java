package edu.algs.graph;

import edu.algs.dset.DSet;
import edu.algs.pqueue.PQueue;
import edu.algs.queue.IntQueue;
import edu.algs.stack.IntStack;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class Graph {
    private final int N;
    private final List<List<Edge>> edges;

    Graph(final int nrOfNodes) {
        if (nrOfNodes <= 0) {
            throw new IllegalStateException("Number of nodes must be positive");
        }
        this.N = nrOfNodes;
        this.edges = new ArrayList<>(N);
        for (int i=0; i<N; i++) {
            this.edges.add(new ArrayList<>());
        }
    }

    public int[][] getMatrix() { return getMatrix(false); }

    public int[][] getMatrix(boolean directed) {
        int result[][] = new int[N][N];
        for (int i=0; i<N; i++) {
            for (Edge e : edges.get(i)) {
                result[e.source][e.target] = e.weight;
                if (!directed) {
                    result[e.target][e.source] = e.weight;
                }
            }
        }
        return result;
    }

    public void edge(Edge e) { edges.get(e.source).add(e); }

    public void edge(int u, int v) { edge(u, v, Edge.W); }

    public void edge(int u, int v, int w) { edge(u, v, w, false); }

    public void edge(int u, int v, int w, boolean symmetric) {
        edges.get(u).add(new Edge(u, v, w));
        if (symmetric) {
            edges.get(v).add(new Edge(v, u, w));
        }
    }

    public List<Edge> adj(int u) { return adj(u, false, false); }
    public List<Edge> sadj(int u) { return adj(u, true, false); }

    public List<Edge> adj(int u, boolean sorted, boolean reverse) {
        if (!sorted) {
            return edges.get(u);
        }  else {
            List<Edge> result = new ArrayList<>(edges.get(u));
            Comparator<Edge> cmp = Edge.ASC;
            if (reverse) {
                cmp = Edge.DESC;
            }
            result.sort(cmp);
            return result;
        }
    }

    public List<Edge> getEdges() { return getEdges(false); }

    public List<Edge> getEdges(boolean sorted) {
        List<Edge> result = new ArrayList<>();
        for (int i=0; i<N; i++) {
            result.addAll(edges.get(i));
        }
        if (sorted) {
            result.sort(Edge.ASC);
        }
        return result;
    }

    public long weight() {
        long sum = 0L;
        for (int i=0; i<N; i++)
            for (Edge e : adj(i))
                sum += e.weight;
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Size size = size();
        sb.append(size.nodes);
        sb.append(" ");
        sb.append(size.edges);
        sb.append("\n");
        for (int i=0; i<size.nodes; i++) {
            for (Edge e : adj(i)) {
                sb.append(e.source);
                sb.append(" ");
                sb.append(e.target);
                sb.append(" ");
                sb.append(e.weight);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public Size size() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            count += adj(i).size();
        }
        return new Size(N, count);
    }

    public static class Size {
        final int nodes;
        final int edges;

        public Size(int nodes, int edges) {
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    // Number of hops (links) from `start` node to `i-th` node
    public int[] hops(int start) {
        int dist[] = new int[N];
        for (int i=0; i<N; i++) {
            dist[i] = -1;
        }
        dist[start] = 0;
        for (Edge e : adj(start)) {
            dist[e.target] = 1;
        }
        BitSet seen = new BitSet(N);
        IntQueue queue = new IntQueue(N);
        queue.offer(start);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            int cost = dist[node];
            seen.set(node);
            for (Edge e : adj(node)) {
                int d = dist[e.target];
                if (d < 0 || d > cost + 1) {
                    dist[e.target] = cost + 1;
                }
                if (!seen.get(e.target)) {
                    queue.offer(e.target);
                }
            }
        }
        return dist;
    }

    // Size of subtree with root int `i-th` node, where `root` is the tree's node (A[root]=N)
    public int[] reach(int root) {
        int size[] = new int[N];
        int prev[] = new int[N]; // trace of chaing of edges from source to current node
        for (int i=0; i<N; i++) { size[i]=1; prev[i]=i; }
        BitSet seen = new BitSet(N);
        IntStack stack = new IntStack(N);
        stack.push(root);
        outer:
        while (!stack.isEmpty()) {
            int n = stack.peek();
            seen.set(n);
            for (Edge e : adj(n)) {
                if (!seen.get(e.target)) {
                    stack.push(e.target);
                    prev[e.target] = n;
                    continue outer;
                }
            }
            stack.pop(); // current node is still `n`
            size[prev[n]] += size[n];
        }
        return size;
    }

    // Sum min paths between for all u...v where u!=v
    public long sumpaths() {
        int root = 0;
        final int tsize[] = reach(root);
        // using atomic long just to keep reference final, to be able to reach it inside lambda, no concurrency expected
        final AtomicLong sum = new AtomicLong(0L);
        dfs(root, null, e -> {
            int n = tsize[e.target];
            sum.addAndGet(e.weight * n * (N-n));
        });
        return sum.get();
    }

    /**
     * Depth-First Search traversal
     *
     * @param start
     * @param node node consumer
     * @param edge edge consumer
     * @return
     */
    public void dfs(int start, Consumer<Integer> node, Consumer<Edge> edge) {
        Edge from[] = new Edge[N]; // trace of chains of edges from source to current node
        BitSet seen = new BitSet(N);
        IntStack stack = new IntStack(N);
        stack.push(start);
        outer:
        while (!stack.isEmpty()) {
            int n = stack.peek();
            seen.set(n);
            for (Edge e : adj(n)) {
                if (!seen.get(e.target)) {
                    stack.push(e.target);
                    from[e.target] = e;
                    continue outer;
                }
            }
            stack.pop(); // current node is still `n`
            if (node != null) node.accept(n);
            if (edge != null && from[n] != null) edge.accept(from[n]);
        }
    }

    /**
     * Breadth-First Search traversal
     *
     * @param start starting node
     * @param node node consumer
     * @param edge edge consumer
     * @return array of distances to all nodes from the starting node, D(s,i) = A[i]
     */
    public void bfs(int start, Consumer<Integer> node, Consumer<Edge> edge) {
        BitSet seen = new BitSet(N);
        IntQueue queue = new IntQueue(N);
        queue.offer(start);
        while(!queue.isEmpty()) {
            int n = queue.poll();
            if (node != null) node.accept(n);
            seen.set(n);
            for (Edge e : adj(n)) {
                if (!seen.get(e.target)) {
                    if (edge != null) edge.accept(e);
                    queue.offer(e.target);
                }
            }
        }
    }

    /**
     * Dijkstra's algorithm finds shortest paths' lengths from starting node for weighted graph
     * See: https://en.wikipedia.org/wiki/Dijkstra's_algorithm
     *
     * @param s starting node
     * @return array of distances to all nodes from the starting node, D(s,i) = A[i]
     */
    public long[] dijkstra(int s) {
        long INF = Integer.MAX_VALUE;
        long dist[] = new long[N];
        for (int i=0; i<N; i++) if (i != s) dist[i] = INF;

        PQueue<Integer> pq = new PQueue<>(N);
        for (int i=0; i<N; i++) pq.add(i, dist[i]);

        while (!pq.isEmpty()) {
            int u = pq.top();
            for (Edge e : adj(u)) {
                int v = e.target;
                int w = e.weight;
                long alt = dist[u] + w;
                if (dist[v] > alt) {
                    dist[v] = alt;
                    pq.set(v, alt);
                }
            }
        }

        for (int i=0; i<N; i++) if (dist[i] == INF) dist[i] = -1;
        return dist;
    }

    /**
     * Floyd-Warshall algorithm to find shortest distance between all pairs of nodes in a weighted graph
     * See: https://en.wikipedia.org/wiki/Floyd-Warshall_algorithm
     *
     * @return Matrix of minimal distance between pair of nodes in the graph, D(i,j) = M[i][j]
     */
    public long[][] floyd() {
        final long INF = Long.MAX_VALUE/2; // avoid overflow when comparing dist to sum of dists
        long dist[][] = new long [N][N];
        for (int i=0; i<N; i++) for (int j=0; j<N; j++) dist[i][j] = (i == j) ? 0 : INF;
        for (int i=0; i<N; i++) for (Edge e : adj(i)) dist[i][e.target] = e.weight;
        for (int k=0; k<N; k++)
            for (int i=0; i<N; i++)
                for (int j=0; j<N; j++)
                    if (dist[i][j] > dist[i][k] + dist[k][j]) // to avoid overflow here, INF is Long.MAX_VALUE/2
                        dist[i][j] = dist[i][k] + dist[k][j];
        for (int i=0; i<N; i++) for (int j=0; j<N; j++) if (dist[i][j] == INF) dist[i][j] = -1;
        return dist;
    }

    /**
     * Kruskal's algorithm finds edges that form Minimum Spanning Tree of a graph.
     * See: https://en.wikipedia.org/wiki/Kruskal's_algorithm
     *
     * @return
     */
    public List<Edge> kruskal() {
        DSet dset = new DSet(N);
        List<Edge> all = getEdges(true);
        List<Edge> used = new ArrayList<>();
        for (int i=0; i<all.size(); i++) {
            Edge e = all.get(i);
            if (dset.find(e.source) != dset.find(e.target)) {
                dset.union(e.source, e.target);
                used.add(e);
            }
        }
        return used;
    }

    /**
     * Prim's Algorithm finds MST of weighted undirected graph
     * See: https://en.wikipedia.org/wiki/Prim's_algorithm
     *
     * @param s starting node (default is 0)
     * @return list of edges selected for MST
     */
    public List<Edge> prim(int s) {
        final int M = size().edges;
        PQueue<Edge> pq = new PQueue<>(M);
        for (Edge e : adj(s)) pq.add(e, e.weight);
        BitSet seen = new BitSet(N);
        seen.set(s);
        List<Edge> tree = new ArrayList<>(N-1);
        while (!pq.isEmpty()) {
            Edge e = pq.top();
            int u = e.source;
            int v = e.target;
            if (seen.get(u) && !seen.get(v)) {
                tree.add(e.normal());
                seen.set(v);
                for (Edge p : adj(v)) {
                    pq.add(p, p.weight);
                }
            }
        }
        tree.sort(Edge.ASC);
        return tree;
    }
    public List<Edge> prim() { return prim(0); }
}
