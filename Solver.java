/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int solveMoves;
    private Stack<Board> solutionStack;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException("tiles are null");
        }
        if (initial.isGoal()) {
            solveMoves = 0;
            TreeNode first = new TreeNode(initial, 0, null);
            constructSolution(first);
        }
        else {
            Board altInitial = initial.twin();
            MinPQ<TreeNode> altMinPQ = new MinPQ<>();
            TreeNode altFirst = new TreeNode(altInitial, 0, null);
            altMinPQ.insert(altFirst);

            MinPQ<TreeNode> minPQ = new MinPQ<>();
            TreeNode first = new TreeNode(initial, 0, null);
            minPQ.insert(first);
            while (!minPQ.isEmpty() && !altMinPQ.isEmpty()) {
                for (Board neighbor : minPQ.min().board.neighbors()) {
                    if (minPQ.min().prevNode != null) {
                        if (!neighbor.equals(minPQ.min().prevNode.board)) {
                            TreeNode neighborNode = new TreeNode(neighbor, minPQ.min().moves + 1,
                                                                 minPQ.min());
                            minPQ.insert(neighborNode);
                        }
                    }
                    else {
                        TreeNode neighborNode = new TreeNode(neighbor, minPQ.min().moves + 1,
                                                             minPQ.min());
                        minPQ.insert(neighborNode);
                    }
                }
                if (minPQ.min().board.isGoal()) {
                    constructSolution(minPQ.min());
                    break;
                }
                minPQ.delMin();
                for (Board altNeighbor : altMinPQ.min().board.neighbors()) {
                    if (altMinPQ.min().prevNode != null) {
                        if (!altNeighbor.equals(altMinPQ.min().prevNode.board)) {
                            TreeNode altNeighborNode = new TreeNode(altNeighbor,
                                                                    altMinPQ.min().moves + 1,
                                                                    altMinPQ.min());
                            altMinPQ.insert(altNeighborNode);
                        }
                    }
                    else {
                        TreeNode altNeighborNode = new TreeNode(altNeighbor,
                                                                altMinPQ.min().moves + 1,
                                                                altMinPQ.min());
                        altMinPQ.insert(altNeighborNode);
                    }
                }
                if (altMinPQ.min().board.isGoal()) {
                    solveMoves = -1;
                    solutionStack = null;
                    break;
                }
            }
        }
    }

    private class TreeNode implements Comparable<TreeNode> {
        private Board board;
        private int moves = 0;
        private int manhattanPriority = 0;
        // private int hammingPriority = 0;
        private TreeNode prevNode;

        private TreeNode(Board givenBoard, int givenMoves, TreeNode givenPrev) {
            board = givenBoard;
            moves = givenMoves;
            prevNode = givenPrev;
            manhattanPriority = moves + board.manhattan();
            // hammingPriority = moves + board.hamming();
        }

        public int compareTo(TreeNode other) {
            // return Integer.compare(this.hammingPriority, other.hammingPriority);
            return Integer.compare(this.manhattanPriority, other.manhattanPriority);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (solveMoves >= 0) {
            return true;
        }
        return false;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solveMoves;
    }

    private void constructSolution(TreeNode finalRef) {
        solutionStack = new Stack<Board>();
        TreeNode curr = finalRef;
        while (curr != null) {
            solutionStack.push(curr.board);
            curr = curr.prevNode;
        }
        if (finalRef != null) {
            solveMoves = solutionStack.size() - 1;
        }
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solutionStack;
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
