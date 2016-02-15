import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte OPEN = 1;
    private static final byte TOP = 2;
    private static final byte BOTTOM = 4;
    private static final byte PERCOLATION = TOP | BOTTOM;

    private final int N;
    private WeightedQuickUnionUF uf;
    private byte[] open;
    private boolean percolates = false;

    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        this.N = N;
        this.uf = new WeightedQuickUnionUF(N * N);

        this.open = new byte[N * N];

        for (int i = 0; i < N; i++) {
            open[i] |= TOP;
            open[open.length - 1 - i] |= BOTTOM;
        }
    }

    private int idx(int i, int j) {
        return (i - 1) * N + j - 1;
    }

    private byte union(int p, int q) {
        int pRoot = uf.find(p);
        int qRoot = uf.find(q);

        open[pRoot] |= open[qRoot] & PERCOLATION;
        open[qRoot] |= open[pRoot] & PERCOLATION;

        byte newPerc = (byte) (open[qRoot] & PERCOLATION);
        uf.union(p, q);

        return newPerc;
    }

    public void open(int i, int j) {
        checkBoundary(i, j);
        int openIdx = idx(i, j);

        if (i > 1 && isOpen(i - 1, j))
            open[openIdx] |= union(openIdx, idx(i - 1, j));
        if (i < N && isOpen(i + 1, j))
            open[openIdx] |= union(openIdx, idx(i + 1, j));

        if (j > 1 && isOpen(i, j - 1))
            open[openIdx] |= union(openIdx, idx(i, j - 1));
        if (j < N && isOpen(i, j + 1))
            open[openIdx] |= union(openIdx, idx(i, j + 1));

        open[openIdx] |= OPEN;
        percolates = percolates || (open[openIdx] & PERCOLATION) == PERCOLATION;
    }

    private void checkBoundary(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException();
    }

    public boolean isOpen(int i, int j) {
        checkBoundary(i, j);
        return (open[idx(i, j)] & OPEN) == OPEN;
    }

    public boolean isFull(int i, int j) {
        checkBoundary(i, j);
        int idx = idx(i, j);
        return (open[idx(i, j)] & OPEN) == OPEN &&
                (open[uf.find(idx)] & TOP) == TOP;
    }

    public boolean percolates() {
        return percolates;
    }

    public static void main(String[] args)  // test client (optional)
    {
    }
}
