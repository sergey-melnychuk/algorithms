package edu.algs.queue;

public class IntQueue {
    private final int N;
    private final int items[];
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public IntQueue(int nrOfItems) {
        this.N = nrOfItems;
        this.items = new int[N];
    }

    public void offer(int item) {
        if (size >= N) {
            throw new IllegalStateException("Queue capacity overflow");
        } else {
            size += 1;
            items[tail] = item;
            tail = (head + size) % N;
        }
    }

    public int poll() {
        if (size == 0) {
            throw new IllegalStateException("Poll from empty queue");
        }
        int top = items[head];
        size -= 1;
        head = (head+1)%N;
        return top;
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Peek from empty queue");
        }
        return items[head];
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int len() { return size; }

    public int cap() {
        return N - size;
    }

    private int max(int x, int y) { if (x >= y) return x; else return y; }
}
