import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first;
    private Node last;
    private int count;


    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item outPut = first.item;
        if (first != last) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
            last = null;
        }
        count--;
        return outPut;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item outPut = last.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        count--;
        return outPut;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item outPut = current.item;
            current = current.next;
            return outPut;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        StdOut.println(dq.isEmpty());
        dq.addFirst(2);
        StdOut.println(dq.size() == 1);
        StdOut.println(!dq.isEmpty());
        dq.addFirst(1);
        StdOut.println(dq.size() == 2);
        dq.addLast(3);
        StdOut.println(dq.size() == 3);
        Iterator<Integer> it = dq.iterator();
        while (it.hasNext()) {
            StdOut.printf("loop item : %d\n", it.next());
        }
        StdOut.printf("First item : %s\n", dq.removeFirst()); // 1
        StdOut.println(dq.size() == 2);
        StdOut.printf("Last item : %d\n", dq.removeLast()); // 3
        StdOut.println(dq.size() == 1);
        StdOut.println(!dq.isEmpty());
        StdOut.printf("Last item : %d\n", dq.removeLast()); // 2
        StdOut.println(dq.size() == 0);
        StdOut.println(dq.isEmpty());
        dq.addLast(1);
        dq.addLast(2);
        StdOut.println(dq.size() == 2);
        StdOut.printf("First item : %d\n", dq.removeFirst()); // 1
        StdOut.printf("First item : %d\n", dq.removeFirst()); // 2

    }
}
