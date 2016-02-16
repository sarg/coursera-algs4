import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private static class SolveStep implements Comparable<SolveStep> {
        private final int step;
        private final Board board;
        private final SolveStep prev;

        public SolveStep(int step, Board board, SolveStep prev) {
            this.step = step;
            this.board = board;
            this.prev = prev;
        }

        private int priority() {
            return step + board.manhattan();
        }

        @Override
        public String toString() {
            return priority() + " " + board.toString();
        }

        @Override
        public int compareTo(SolveStep o) {
            return Integer.compare(priority(), o.priority());
        }
    }

    private final LinkedStack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SolveStep> pq = new MinPQ<SolveStep>();
        MinPQ<SolveStep> pqTwin = new MinPQ<SolveStep>();
        pq.insert(new SolveStep(0, initial, null));
        pqTwin.insert(new SolveStep(0, initial.twin(), null));

        SolveStep lastStep;
        do {
            lastStep = makeStep(pq);
        } while (lastStep == null && makeStep(pqTwin) == null);

        if (lastStep != null) {
            solution = new LinkedStack<Board>();
            while (lastStep != null) {
                solution.push(lastStep.board);
                lastStep = lastStep.prev;
            }
        } else {
            solution = null;
        }
    }

    private SolveStep makeStep(MinPQ<SolveStep> pq) {
        SolveStep lastStep = pq.delMin();
        if (lastStep.board.isGoal())
            return lastStep;

        for (Board nb : lastStep.board.neighbors()) {
            if (lastStep.prev == null || !nb.equals(lastStep.prev.board))
                pq.insert(new SolveStep(lastStep.step + 1, nb, lastStep));
        }

        return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable())
            return solution.size() - 1;

        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    }
}