package edu.algs.tree;

import java.util.Objects;

class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    final T val;
    Node<T> parent;
    Node<T> lo;
    Node<T> hi;
    int size;
    int dep;

    Node(Node<T> parent, T val) {
        this.parent = parent;
        this.val = val;
        this.size = 1;
        this.dep = 1;
    }

    void touch() {
        size = 1 + ((lo == null) ? 0 : lo.size) + ((hi == null) ? 0 : hi.size);
        dep = 1 + Math.max(((lo == null) ? 0 : lo.dep), ((hi == null) ? 0 : hi.dep));
    }

    int ds() { return ((lo == null) ? 0 : lo.size) - ((hi == null) ? 0 : hi.size); }

    @Override
    public int compareTo(Node<T> that) { return this.val.compareTo(that.val); }

    @Override
    public String toString() { return "Node{" + val.toString() + "}"; }

    @Override
    public int hashCode() { return Objects.hash(val, size, dep); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return (size == node.size) && (dep == node.dep) && Objects.equals(val, node.val);
    }

    static <T extends Comparable<T>> Node<T> value(Node<T> parent, T value) { return new Node<>(parent, value); }
}
