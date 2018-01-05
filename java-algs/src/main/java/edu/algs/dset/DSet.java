package edu.algs.dset;

// Disjoint Set

public class DSet {
    private final int N;
    private final int root[];
    private final int rank[];

    public DSet(int nrOfItems) {
        this.N = nrOfItems;
        this.root = new int[N];
        this.rank = new int[N];
        for (int i=0; i<N; i++) {
            root[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        if (x < 0 || x >= N) {
            throw new IllegalArgumentException("Invalid node index: " + x);
        }
        while (root[x] != x) {
            x = root[x];
        }
        return x;
    }

    public void union(int s, int t) {
        int sg = find(s);
        int tg = find(t);
        if (sg == tg) return;

        if (rank[sg] <= rank[tg]) {
            root[sg] = tg;
            rank[tg] += rank[sg];
        } else {
            root[tg] = sg;
            rank[sg] += rank[tg];
        }
    }
}
