/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int solveMoves;
    private Queue<Board> solutionBoard = new Queue<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        int trackMoves = 0;
        MinPQ<TreeNode> minPQ = new MinPQ<>();
        TreeNode first = new TreeNode(initial, trackMoves);
        minPQ.insert(first);
        Board prevBoard = first.board;

        Board altInitial = initial.twin();
        int altTrackMoves = 0;
        MinPQ<TreeNode> altMinPQ = new MinPQ<>();
        TreeNode altFirst = new TreeNode(altInitial, altTrackMoves);
        altMinPQ.insert(altFirst);
        Board altPrevBoard = altFirst.board;

        while (!minPQ.isEmpty() || !altMinPQ.isEmpty()) {
            Board deletedMin;
            for (Board neighbor : minPQ.min().board.neighbors()) {
                if (!neighbor.equals(prevBoard)) {
                    trackMoves++;
                    TreeNode neighborNode = new TreeNode(neighbor, trackMoves);
                    minPQ.insert(neighborNode);
                    prevBoard = neighbor;
                }
            }
            deletedMin = minPQ.delMin().board;
            solutionBoard.enqueue(deletedMin);
            if (deletedMin.isGoal()) {
                solveMoves = trackMoves;
                break;
            }
            for (Board altNeighbor : altMinPQ.min().board.neighbors()) {
                if (!altNeighbor.equals(altPrevBoard)) {
                    altTrackMoves++;
                    TreeNode altNeighborNode = new TreeNode(altNeighbor, altTrackMoves);
                    altMinPQ.insert(altNeighborNode);
                    altPrevBoard = altNeighbor;
                }
            }
            if (altMinPQ.delMin().board.isGoal()) {
                solveMoves = -1;
                break;
            }
        }
    }

    private class TreeNode implements Comparable<TreeNode> {
        private Board board;
        private int moves = 0;
        private int manhattanPriority = 0;
        private int hammingPriority = 0;

        private TreeNode(Board givenBoard, int givenMoves) {
            board = givenBoard;
            moves = givenMoves;
            manhattanPriority = moves + board.manhattan();
            hammingPriority = moves + board.hamming();
        }

        public int compareTo(TreeNode other) {
            return Integer.compare(this.hammingPriority, other.hammingPriority);
            // return Integer.compare(this.manhattanPriority, other.manhattanPriority);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (solveMoves > 0) {
            return true;
        }
        return false;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solveMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solutionBoard;
        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
