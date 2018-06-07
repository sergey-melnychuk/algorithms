package edu.algs.graph.algs;

import edu.algs.graph.Edge;
import edu.algs.graph.Graph;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Stack;

/**
 * https://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm
 */
public class Tarjan {
    private final Graph graph;
    private final int N;

    private int[] low;
    private int[] idx;
    private int index;
    private Stack<Integer> stack;
    private BitSet onStack;
    private int[] components;
    private int component;

    public Tarjan(Graph graph) {
        this.graph = graph;
        this.N = graph.size().nodes;
    }

    private void init() {
        this.low = new int[N];
        this.idx = new int[N];
        for (int i=0; i<N; i++) idx[i] = -1;
        this.stack = new Stack<>();
        this.index = 0;
        this.onStack = new BitSet(N);
        this.components = new int[N];
        this.component = -1;
    }

    public int[] run() {
        init();
        for (int v=0; v<N; v++) {
            if (idx[v] == -1) {
                strongconnect(v);
            }
        }
        return Arrays.copyOf(components, components.length);
    }

    private void strongconnect(int v) {
        idx[v] = index;
        low[v] = index;
        index += 1;
        stack.push(v);
        onStack.set(v);

        for (Edge edge : graph.adj(v)) {
            int w = edge.target;
            if (idx[w] == -1) {
                strongconnect(w);
                low[v] = Math.min(low[v], low[w]);
            } else if (onStack.get(w)) {
                low[v] = Math.min(low[v], idx[w]);
            }
        }

        if (low[v] == idx[v]) {
            component += 1;
            int w = 0;
            do {
                w = stack.pop();
                onStack.clear(w);
                components[w] = component;
            } while (w != v);
        }
    }
}
