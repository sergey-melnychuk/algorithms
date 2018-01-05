package edu.algs.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTest {

    @Test
    void swap() {
        int a[] = {0,1,2};

        Array.swap(a, 0, 1);
        assertEquals(Arrays.asList(1, 0, 2), list(a));

        Array.swap(a, 1, 2);
        assertEquals(Arrays.asList(1, 2, 0), list(a));

        Array.swap(a, 2, 1);
        assertEquals(Arrays.asList(1, 0, 2), list(a));

        Array.swap(a, 1, 0);
        assertEquals(Arrays.asList(0, 1, 2), list(a));
    }

    private List<Integer> list(int a[]) {
        return Arrays.stream(a).boxed().collect(Collectors.toList());
    }
}
