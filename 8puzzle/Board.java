import edu.princeton.cs.algs4.LinkedStack;

import java.util.Arrays;

public class Board {
    final private int emptyIdx;
    final private int[] board;
    final private int N;
    final private boolean goal;

    // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N * N];

        int c = 0;
        boolean isGoal = true;
        int tmpEmpty = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++, c++) {
                board[c] = blocks[i][j];
                if (board[c] == 0)
                    tmpEmpty = c;

                isGoal = isGoal && !isBlockMisplaced(c);
            }
        }

        if (tmpEmpty == -1)
            throw new IllegalArgumentException();

        emptyIdx = tmpEmpty;
        goal = isGoal;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    private boolean isBlockMisplaced(int c) {
        return c != emptyIdx && board[c] - c != 1;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            if (isBlockMisplaced(i))
                hamming++;
        }
        return hamming;
    }

    private static int abs(int j) {
        return j < 0 ? -j : j;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            if (i == emptyIdx)
                continue;

            int r = (board[i] - 1) / N;
            int c = (board[i] - 1) % N;

            manhattan += abs(r - (i / N)) + abs(c - (i % N));
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return goal;
    }

    private Board(Board from, int i, int j) {
        N = from.N;
        board = Arrays.copyOf(from.board, from.board.length);

        int tmp = board[i];
        board[i] = board[j];
        board[j] = tmp;

        goal = manhattan() == 0;
        if (board[i] == 0)
            emptyIdx = i;
        else if (board[j] == 0)
            emptyIdx = j;
        else emptyIdx = from.emptyIdx;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int i = 0;
        if (i == emptyIdx) i += 1;

        int j = i + 1;
        if (j == emptyIdx) j += 1;

        return new Board(this, i, j);
    }

    // does this board equal y?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board b = (Board) o;
        return N == b.N && Arrays.equals(board, b.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int r = emptyIdx / N;
        int c = emptyIdx % N;

        LinkedStack<Board> nbs = new LinkedStack<Board>();
        if (r > 0) nbs.push(new Board(this, emptyIdx, emptyIdx - N));
        if (r < N - 1) nbs.push(new Board(this, emptyIdx, emptyIdx + N));

        if (c > 0) nbs.push(new Board(this, emptyIdx, emptyIdx - 1));
        if (c < N - 1) nbs.push(new Board(this, emptyIdx, emptyIdx + 1));

        return nbs;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();

        String width = "% " + (log10(board.length) + 1) + "d";

        for (int i = 0; i < board.length; i++) {
            if (i > 0 && i % N == 0)
                sb.append('\n');
            sb.append(String.format(width, board[i]));
        }

        sb.append('\n');
        return sb.toString();
    }

    private int log10(int t) {
        int ld = 0;
        while (t > 0) {
            t /= 10;
            ld++;
        }
        return ld;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
    }
}