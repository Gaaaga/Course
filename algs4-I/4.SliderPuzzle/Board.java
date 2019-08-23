import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public class Board {
    private int[][] tiles;
    private int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        this.tiles = newTiles;
    }

    // string representation of this board
    public String toString() {
        String result = "";
        result += String.valueOf(size);
        result += "\n";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result += tiles[i][j];
                result += " ";
            }
            result += "\n";
        }
        return result;
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int goalTile = i * size + j + 1;
                int actualTile = tiles[i][j];
                if (!(actualTile == 0)) {
                    if (goalTile != actualTile) {
                        result += 1;
                    }
                }
            }
        }
        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tile = tiles[i][j];
                if (tile != 0) {
                    int goalX = expectX(tile);
                    int goalY = expectY(tile);

                    int actualX = i;
                    int actualY = j;

                    result += Math.abs(goalX - actualX);
                    result += Math.abs(goalY - actualY);
                }
            }
        }
        return result;
    }

    private int expectX(int item) {
        if (item == 0) {
            return size - 1;
        }
        if (item <= size) {
            return 0;
        }
        if (item > (size * size - size)) {
            return size - 1;
        }
        if (item % size == 0) {
            return item / size - 1;
        }
        return item / size;
    }

    private int expectY(int item) {
        if (item == 0) {
            return size - 1;
        }
        if (item % size == 0) {
            return size - 1;
        }
        return item % size - 1;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != size - 1 && j != size - 1) {
                    if (tiles[i][j] != i * size + j + 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        Board copy = (Board) y;
        if (copy.size != this.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != copy.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> result = new Stack<Board>();
        int[][] copy = new int[size][size];
        int x = -1;
        int y = -1;
        boolean found = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }
        if (x > 0) {
            result.push(swap(x, y, x - 1, y));
        }
        if (x < size - 1) {
            result.push(swap(x, y, x + 1, y));
        }
        if (y > 0) {
            result.push(swap(x, y, x, y - 1));
        }
        if (y < size - 1) {
            result.push(swap(x, y, x, y + 1));
        }
        return result;
    }

    private Board swap(int x0, int y0, int x1, int y1) {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == x0 && j == y0) {
                    copy[i][j] = tiles[x1][y1];
                } else if (i == x1 && j == y1) {
                    copy[i][j] = tiles[x0][y0];
                } else {
                    copy[i][j] = tiles[i][j];
                }
            }
        }
        Board result = new Board(copy);
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] == 0 || tiles[0][1] == 0) {
            return swap(size - 1, 0, size - 1, 1);
        } else {
            return swap(0, 0, 0, 1);
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] data = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board = new Board(data);
        StdOut.println(board.toString());
        StdOut.println(board.hamming() == 0);
        StdOut.println(board.manhattan() == 0);
        StdOut.println(board.isGoal() == true);
        StdOut.println(board.twin().toString());
        int[][] data2 = new int[][] { { 1, 2, 4 }, { 5, 3, 0 }, { 7, 8, 6 } };
        Board board2 = new Board(data2);
        StdOut.println(board.equals(board2) == false);
        StdOut.println(board2.hamming() == 4);
        StdOut.println(board2.manhattan() == 7);
        StdOut.println(board2.toString());
        StdOut.println(board2.isGoal() == false);
        StdOut.println(board2.twin().toString());
    }

}
