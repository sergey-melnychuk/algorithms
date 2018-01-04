package edu.algs.stack;

public class IntStack {
    private final int N;
    private final int items[];
    private int top = 0;

    public IntStack(int nrOfItems) {
        this.N = nrOfItems;
        this.items = new int[N];
    }

    public void push(int item) {
        if (top == N) {
            throw new IllegalStateException("Stack overflow");
        } else {
            items[top] = item;
            top += 1;
        }
    }

    public int pop() {
        if (top == 0) {
            throw new IllegalStateException("Stack underflow");
        } else {
            top -= 1;
            return items[top];
        }
    }

    public int peek() {
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
