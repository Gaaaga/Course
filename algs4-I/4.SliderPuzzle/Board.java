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
        String result = '';
        result += String.valueOf(size);
        result += '\n';
        for (int i = 0; i < size; i++) {
            for (int j = o; j < size; j++) {
                result += tiles[i][j];
                result += ' ';
            }
            result += '\n';
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
                    return false
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()

    // unit testing (not graded)
    public static void main(String[] args)

}
