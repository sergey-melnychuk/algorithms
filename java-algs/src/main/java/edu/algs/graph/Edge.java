package edu.algs.graph;

import java.util.Comparator;
import java.util.Objects;

public class Edge {
    static final int W = 1; // default weight of the edge (used in un-weighted graph)

    public final int source;
    public final int target;
    public final int weight;

    public Edge(int source, int target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public Edge(int source, int target) {
        this(source, target, W);
    }

    public Edge reverse() {
        return new Edge(target, source, weight);
    }

    public Edge normal() {
        if (source <= target) return this;
        else return reverse();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return source == edge.source &&
                target == edge.target &&
                weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, weight);
    }

    @Override
    public String toString() {
        return "Edge{" + source + "->" + target + ", w=" + weight + '}';
    }

    static Comparator<Edge> ASC = (Edge lhs, Edge rhs) -> {
        if (lhs.weight != rhs.weight) return (int) (lhs.weight - rhs.weight);
        if (lhs.source != rhs.source) return lhs.source - rhs.source;
        if (lhs.target != rhs.target) return lhs.target - rhs.target;
        return 0;
    };

    static Comparator<Edge> DESC = (Edge lhs, Edge rhs) -> ASC.compare(rhs, lhs);
}
