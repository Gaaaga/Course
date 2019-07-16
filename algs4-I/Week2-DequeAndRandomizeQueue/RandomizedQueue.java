import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        count = 0;
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count <= 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        items[count] = item;
        count++;
        if (count == items.length) {
            resize(items.length * 2);
        }
    }

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomPoint = StdRandom.uniform(count);
        Item outPut = items[randomPoint];
        items[randomPoint] = items[count - 1];
        items[count - 1] = null;
        count--;
        if (count <= items.length / 4) {
            resize(items.length / 2);
        }
        return outPut;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomPoint = StdRandom.uniform(count);
        return items[randomPoint];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private final int[] set = new int[count];
        private int current;

        public ListIterator() {
            current = 0;
            for (int i = 0; i < count; i++) {
                set[i] = i;
            }
            StdRandom.shuffle(set);
        }

        public boolean hasNext() {
            return current < count;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            } else {
                Item output = items[set[current]];
                current++;
                return output;
            }

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println(rq.isEmpty());
        StdOut.println(rq.size() == 0);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        Iterator<Integer> t = rq.iterator();
        while (t.hasNext()) {
            StdOut.printf("loop item : %d\n", t.next());
        }
        StdOut.println(!rq.isEmpty());
        StdOut.println(rq.size() == 5);
        StdOut.println(rq.sample());
        StdOut.println(rq.size() == 5);
        StdOut.println(rq.dequeue());
        StdOut.println(rq.size() == 4);
        StdOut.println(rq.dequeue());
        StdOut.println(rq.size() == 3);
        StdOut.println(rq.dequeue());
        StdOut.println(rq.size() == 2);

        StdOut.println(rq.dequeue());
        StdOut.println(rq.size() == 1);

        StdOut.println(!rq.isEmpty());
        StdOut.println(rq.dequeue());
        StdOut.println(rq.size() == 0);

        StdOut.println(rq.isEmpty());
    }

}
