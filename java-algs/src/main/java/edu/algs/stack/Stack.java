package edu.algs.stack;

@SuppressWarnings("unchecked")
public class Stack<T> {
    private final int N;
    private final T[] items;
    private int top = 0;

    public Stack(int nrOfItems) {
        this.N = nrOfItems;
        this.items = (T[]) new Object[N];
    }

    public void push(T item) {
        if (top == N) {
            throw new IllegalStateException("Stack overflow");
        } else {
            items[top] = item;
            top += 1;
        }
    }

    public T pop() {
        if (top == 0) {
            throw new IllegalStateException("Stack underflow");
        } else {
            top -= 1;
            return items[top];
        }
    }

    public T peek() {
        if (top == 0) {
            throw new IllegalStateException("Peek from empty stack");
        } else {
            return items[top-1];
        }
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int len() {
        return top;
    }

    public int cap() {
        return N - top;
    }
}
