/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        int trackMoves = 0;
        MinPQ<TreeNode> minPQ = new MinPQ<>();
        TreeNode first = new TreeNode(initial, trackMoves);
        minPQ.insert(first);
        MinPQ<TreeNode> gameTree = new MinPQ<>();
        gameTree.insert(first);

        while (!minPQ.isEmpty()) {
            for (Board neighbor : minPQ.min().board.neighbors()) {
                TreeNode neighborNode = new TreeNode(neighbor, trackMoves);
                minPQ.insert(neighborNode);
            }

        }
    }

    private class TreeNode {
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
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {

    }

    // min number of moves to solve initial board
    public int moves() {

    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {

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
