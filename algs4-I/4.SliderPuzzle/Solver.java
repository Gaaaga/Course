import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    boolean isSolved;
    Node goalNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        Node thisBoard = new Node(initial, 0, null);
        Node twinBoard = new Node(initial.twin(), 0, null);
        MinPQ<Node> thisPQ = new MinPQ<Node>(new Priority());
        MinPQ<Node> twinPQ = new MinPQ<Node>(new Priority());
        thisPQ.insert(thisBoard);
        twinPQ.insert(twinBoard);
        Node thisMinNode = thisPQ.delMin();
        Node twinMinNode = twinPQ.delMin();
        while (!thisMinNode.board.isGoal() || !twinMinNode.board.isGoal()) {
            for (Board b : thisMinNode.board.neighbors()) {
                if (thisMinNode.parent == null || !b.equals(thisMinNode.parent.board)) {
                    Node newNode = new Node(b, thisMinNode.steps + 1, thisMinNode);
                    thisPQ.insert(newNode);
                }
            }
            for (Board b : twinMinNode.board.neighbors()) {
                if (thisMinNode.parent == null || !b.equals(twinMinNode.parent.board)) {
                    Node newNode = new Node(b, twinMinNode.steps + 1, twinMinNode);
                    thisPQ.insert(newNode);
                }
            }
            thisMinNode = thisPQ.delMin();
            twinMinNode = twinPQ.delMin();
            if (thisMinNode.board.isGoal()) {
                this.isSolved = true;
                this.goalNode = thisMinNode;
            }
            if (twinMinNode.board.isGoal()) {
                this.isSolved = false;
            }
        }
    }

    private class Node {
        Board board;
        int steps;
        Node parent;
        int hamming;
        int manhattan;

        Node(Board b, int s, Node p) {
            board = b;
            steps = s;
            parent = p;
            this.hamming = b.hamming();
            this.manhattan = b.manhattan();
        }
    }

    private class Priority implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return countTotal(a) - countTotal(b);
        }
    }

    private int countTotal(Node a) {
        int total = a.hamming + a.steps;
        return total;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolved;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolved) {
            return goalNode.steps;
        }
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolved) {
            return new SolutionIterable();
        }
        return null;
    }

    private class SolutionIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new SolutionIterator();
        }
    }

    private class SolutionIterator implements Iterator<Board> {
        private int count = 0;
        private int total;
        Board[] boards;

        public SolutionIterator() {
            Node countNode = goalNode;
            while (countNode.parent != null) {
                total += 1;
                countNode = countNode.parent;
            }
            boards = new Board[total];
            Node thisNode = goalNode;
            for (int i = 0; i < boards.length; i++) {
                boards[boards.length - i - 1] = thisNode.board;
                thisNode = thisNode.parent;
            }
        }

        public boolean hasNext() {
            return count == total;
        }

        public Board next() {
            count += 1;
            return boards[count];
        }
    }

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
