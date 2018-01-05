package edu.algs.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntQueueTest {

    @Test
    void emptyPoll() {
        final IntQueue q = new IntQueue(3);
        Throwable t = assertThrows(IllegalStateException.class, q::poll);
        assertEquals("Poll from empty queue", t.getMessage());
    }

    @Test
    void emptyPeek() {
        final IntQueue q = new IntQueue(3);
        Throwable t = assertThrows(IllegalStateException.class, q::peek);
        assertEquals("Peek from empty queue", t.getMessage());
    }

    @Test
    void overflow() {
        final IntQueue q = new IntQueue(3);
        for (int i=0; i<3; i++) q.offer(i);
        Throwable t = assertThrows(IllegalStateException.class, () -> q.offer(100));
        assertEquals("Queue capacity overflow", t.getMessage());
    }

    @Test
    void queue() {
        final int n = 10;
        final IntQueue q = new IntQueue(n);
        assertEquals(0, q.len());
        assertEquals(n, q.cap());
        for (int i=0; i<n; i++) {
            q.offer(i);
            assertEquals(i+1, q.len());
            assertEquals(n-1-i, q.cap());
        }
        for (int i=0; i<n; i++) {
            assertEquals(i, q.peek());
            assertEquals(i, q.poll());
            assertEquals(n-1-i, q.len());
            assertEquals(i+1, q.cap());
        }
    }

    /*
    0          q1         m          q2         n
    |----------|----------|----------|----------|
    |################################|----------| fill 3/4 of the queue
    |---------------------|##########|----------| poll 1/2 of the queue
    |---------------------|##########|$$$$$$$$$$| fill 1/4 of the queue
    |$$$$$$$$$$$$$$$$$$$$$|##########|$$$$$$$$$$| fill 1/2 of the queue - head rolls over to the beginning of the array
    |$$$$$$$$$$$$$$$$$$$$$|----------|$$$$$$$$$$| poll 1/4 of the queue
    |$$$$$$$$$$$$$$$$$$$$$|----------|----------| poll 1/2 of the queue
    |---------------------|----------|----------|
     */
    @Test
    void rollover() {
        final int n = 400;
        int m = n / 2;
        int q1 = m / 2;
        int q3 = 3 * q1;

        final IntQueue q = new IntQueue(n);
        final int base1 = 0;
        for (int i=0; i<q3; i++) q.offer(base1 + i);
        for (int i=0; i<m; i++) assertEquals(i, q.poll());
        final int base2 = 1000;
        for (int i=0; i<n-q1; i++) q.offer(base2 + i);
        for (int i=m; i<q3; i++) assertEquals(base1 + i, q.poll());
        for (int i=0; i<n-q1; i++) assertEquals(base2 + i, q.poll());
    }

}
