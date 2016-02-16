import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testNeighbors() throws Exception {
        Board b = getBoard("puzzle3x3-14.txt");
        System.out.println(b);
        for (Board n: b.neighbors()) {
            System.out.println(n);
        }

    }

    @Test
    public void testIsGoal() throws Exception {
        // create initial board from file
        Board goalBoard = getBoard("puzzle4x4-00.txt");
        assertEquals(0, goalBoard.manhattan());
        assertEquals(0, goalBoard.hamming());
        assertTrue(goalBoard.isGoal());
    }

    @Test
    public void test() throws Exception {

        Solver solver = new Solver(getBoard("puzzle3x3-unsolvable.txt"));

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


    }

    private Board getBoard(String name) {
        In in = new In(name);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        return new Board(blocks);
    }
}