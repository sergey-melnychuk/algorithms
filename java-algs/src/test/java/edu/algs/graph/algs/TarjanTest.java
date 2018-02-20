package edu.algs.graph.algs;

import edu.algs.graph.Factory;
import edu.algs.graph.Graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TarjanTest {

    @Test
    void tarjan1() {
        Graph g = new Graph(1);
        g.edge(0, 0);

        Tarjan tarjan = new Tarjan(g);

        int[] actual = tarjan.run();
        int[] expected = new int[] {0};

        assertArrayEquals(expected, actual);
    }


    @Test
    void tarjan2() {
        Graph g = new Graph(2);
        g.edge(0, 1);
        g.edge(1, 0);

        Tarjan tarjan = new Tarjan(g);

        int[] actual = tarjan.run();
        int[] expected = new int[] {0,0};

        assertArrayEquals(expected, actual);
    }


    @Test
    void tarjan3() {
        Graph g = new Graph(3);
        g.edge(0, 1);
        g.edge(1, 2);
        g.edge(2, 0);

        Tarjan tarjan = new Tarjan(g);

        int[] actual = tarjan.run();
        int[] expected = new int[] {0,0,0};

        assertArrayEquals(expected, actual);
    }

    @Test
    void tarjan5() {
        String string =
                "5 5  \n" +
                "1 2  \n" +
                "4 2  \n" +
                "2 3  \n" +
                "3 4  \n" +
                "4 5";
        Graph g = Factory.string(string, true, 1);

        Tarjan t = new Tarjan(g);

        int[] actual = t.run();
        int[] expected = new int[] {2,1,1,1,0};

        assertArrayEquals(expected, actual);
    }

    @Test
    void tarjan8() {
        // example graph from wiki page: https://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm
        String string =
                "8 14  \n" +
                "1 2  \n" +
                "2 3  \n" +
                "3 1  \n" +
                "4 2  \n" +
                "4 2  \n" +
                "4 3  \n" +
                "4 5  \n" +
                "5 4  \n" +
                "5 6  \n" +
                "6 7  \n" +
                "7 6  \n" +
                "8 5  \n" +
                "8 7  \n" +
                "8 8";
        Graph g = Factory.string(string, true, 1);

        Tarjan t = new Tarjan(g);

        int[] actual = t.run();
        int[] expected = new int[] {0,0,0,2,2,1,1,3};

        assertArrayEquals(expected, actual);
    }
}
