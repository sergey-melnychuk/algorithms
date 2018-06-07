package edu.algs.custom;

// Modified disjoint set (union-find)
class SuccessorSetImpl implements SuccessorSet {
    private final int n;
    private final int id[];
    private final int val[];
    private final int rank[];

    private SuccessorSetImpl(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        this.n = n;
        this.id = new int[n];
        this.val = new int[n];
        this.rank = new int[n];
        for (int i=0; i<n; i++) {
            id[i] = i;
            val[i] = i;
            rank[i] = 1;
        }
    }

    private int root(int x) {
        while (id[x] != x) x = id[x];
        return x;
    }

    // O(log(N)) worst case
    @Override
    public void remove(int x) {
        if (x < 0 || x >= n) throw new IllegalArgumentException("x must be >= 0 and < n");
        int xroot = root(x);
        if (x == n-1) {
            // Specific case for largest item in the set, as it doesn't have a successor (by problem definition)
            val[xroot] = -1;
        } else {
            int y = x + 1; // the successor's id
            int yroot = root(y);

            if (rank[yroot] >= rank[xroot]) {
                id[xroot] = yroot;
                rank[yroot] += rank[xroot];
            } else {
                id[yroot] = xroot;
                rank[xroot] += rank[yroot];
                val[xroot] = val[yroot]; // update root's value for new sub-set's root
            }
        }
    }

    // O(log(N)) worst case
    @Override
    public int next(int x) {
        if (x < 0 || x >= n) throw new IllegalArgumentException("x must be >= 0 and < n");
        return val[root(x)];
    }

    public static SuccessorSet create(int n) { return new SuccessorSetImpl(n); }
}
