package edu.algs;

import edu.algs.stack.IntStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntStackTest {

    @Test
    void popUnderflow() {
        final IntStack stack = new IntStack(3);
        Throwable t = assertThrows(IllegalStateException.class, stack::pop);
        assertEquals("Stack underflow", t.getMessage());
    }

    @Test
    void peekUnderflow() {
        final IntStack stack = new IntStack(3);
        Throwable t = assertThrows(IllegalStateException.class, stack::peek);
        assertEquals("Peek from empty stack", t.getMessage());
    }

    @Test
    void pushOverflow() {
        final IntStack stack = new IntStack(1);
        stack.push(1);
        Throwable t = assertThrows(IllegalStateException.class, () -> stack.push(0));
        assertEquals("Stack overflow", t.getMessage());
    }

    @Test
    void empty() {
        final IntStack stack = new IntStack(1);
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void peek() {
        final IntStack stack = new IntStack(1);
        stack.push(1);
        assertEquals(1, stack.peek());
        assertFalse(stack.isEmpty());
    }

    @Test
    void pop() {
        final IntStack stack = new IntStack(1);
        stack.push(1);
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void len() {
        final IntStack stack = new IntStack(1);
        assertEquals(0, stack.len());
        stack.push(1);
        assertEquals(1, stack.len());
        stack.pop();
        assertEquals(0, stack.len());
    }

    @Test
    void cap() {
        final IntStack stack = new IntStack(3);
        assertEquals(3, stack.cap());
        stack.push(1);
        assertEquals(2, stack.cap());
        stack.pop();
        assertEquals(3, stack.cap());
    }
}
