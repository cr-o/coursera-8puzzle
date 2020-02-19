/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] boardTiles;
    private int rows;
    private int cols;
    private int[][] goalBoard;

    public Board(int[][] tiles) {
        boardTiles = tiles;
        rows = boardTiles.length;
        cols = boardTiles[0].length;
        makeGoalBoard();
    }

    private void makeGoalBoard() {
        goalBoard = new int[rows][cols];
        int fillVal = 1;
        for (int i = 0; i < rows; i++) {
            for (int n = 0; n < cols; n++) {
                if (i == rows - 1 && n == cols - 1) {
                    fillVal = 0;
                }
                goalBoard[i][n] = fillVal;
                fillVal++;
            }
        }
    }

    private int[][] getGoalBoard() {
        return goalBoard;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringRep = new StringBuilder();
        stringRep.append(Integer.toString(rows));
        stringRep.append("\n");
        for (int i = 0; i < rows; i++) {
            for (int n = 0; n < cols; n++) {
                stringRep.append(" ");
                stringRep.append(Integer.toString(boardTiles[i][n]));
            }
            stringRep.append("\n");
        }
        return stringRep.toString();
    }

    // board dimension n
    public int dimension() {
        return rows;
    }

    // number of tiles out of place
    public int hamming() {
        int compareVal = 1;
        int outOfPlace = 0;
        for (int i = 0; i < rows; i++) {
            for (int n = 0; n < cols; n++) {
                if (boardTiles[i][n] == 0) {
                    continue;
                }
                if (boardTiles[i][n] != compareVal) {
                    outOfPlace++;
                }
                compareVal++;
            }
        }
        return outOfPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int currNum = 0;
        int correctRow = 0;
        int correctCol = 0;
        int offset = 0;
        for (int i = 0; i < rows; i++) {
            for (int n = 0; n < cols; n++) {
                if (boardTiles[i][n] == 0) {
                    continue;
                }
                currNum = (i * rows) + 1 + n;
                if (boardTiles[i][n] != currNum) {
                    correctRow = i / rows;
                    correctCol = n % cols - 1;
                    offset += Math.abs(correctRow - i);
                    offset += Math.abs(correctCol - n);
                }
            }
        }
        return offset;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.deepEquals(boardTiles, getGoalBoard());
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return (Arrays.deepEquals(this.boardTiles, that.boardTiles);

    }

    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();
        // look at spaces directly right, below, left, above the 0 square
        // if within boundaries, swap with the 0 square
        // add to queue
        int emptyRow = 0;
        int emptyCol = 0;
        for (int i = 0; i < rows; i++) {
            for (int n = 0; i < cols; n++) {
                if (boardTiles[i][n] == 0) {
                    emptyRow = i;
                    emptyCol = 0;
                    break;
                }
            }
        }

        return queue;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
