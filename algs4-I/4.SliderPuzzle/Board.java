public class Board {
    private int[][] tiles;
    private int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < tiles[i].length - 1; j++) {
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
            for (int j = o; j < size; j++) {
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
                if (goalTile == actualTile) {
                    result += 1;
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
                int goalX = tile / size - 1;
                int goalY = tile % size - 1;
                int actualX = i;
                int actualY = j;
                result += Math.abs(goalX - actualX);
                result += Math.abs(goalY - actualY);
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != i * size + j + 1) {
                    return false;
                }
            }
        }
        return true
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        if (y.size != tiles.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != y[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> result = new Queue<Board>();
        int[][] copy = new int[size][size];
        int x;
        int y;
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
            if (found) break;
        }
        if (x > 0) {
            result.enqueue(swap(x, y, x - 1, y));
        }
        if (x < size - 1) {
            result.enqueue(swap(x, y, x + 1, y));
        }
        if (y > 0) {
            result.enqueue(swap(x, y, x, y - 1));
        }
        if (y < size - 1) {
            result.enqueue(swap(x, y, x, y + 1));
        }
        return result;
    }

    private Board swap(int x0, int y0, int x1, int y1) {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == x0,j = y0){
                    copy[i][j] = tiles[x1][y1];
                }else if (i == x1,j == y1){
                    copy[i][j] = tiles[x0][y0];
                }else{
                    copy[i][j] = tiles[i][j];
                }
            }
        }
        return new Board(copy);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (tiles[0][0] == 0 || tiles[0][1] == 0) {
            return new Board(swap(size - 1, 0, size - 1, 1))
        } else {
            return new Board(swap(0, 0, 0, 1))
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] data = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }
        Board board = new Board(data);
        StdOut.print(board.toString());
    }

}
