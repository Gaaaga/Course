import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> items = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            items.enqueue(StdIn.readString());
        }
        Iterator<String> it = items.iterator();
        for (int i = 0; i < n; i++) {
            StdOut.println(it.next());
        }
    }
}
