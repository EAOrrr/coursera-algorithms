/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateItem(item);
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateItem(item);
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        emptyCheck();
        size--;
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        emptyCheck();
        size--;
        Item item = last.item;
        last = last.prev;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // check if the item to insert is null
    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // check if the deque is empty when remove elem from the deque
    private void emptyCheck() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // create an empty deque
        Deque<Integer> intDeque = new Deque<>();
        Deque<String> stringQueue = new Deque<>();
        StdOut.println(intDeque.isEmpty());
        StdOut.println(stringQueue.isEmpty());
        StdOut.println(intDeque.size());
        for (String s : stringQueue) {
            StdOut.println(s);
        }
        // test Integer
        // test addFirst
        StdOut.println("test addFirst");
        intDeque.addFirst(2);
        intDeque.addFirst(1);
        for (Integer i : intDeque) {
            StdOut.println(i);
        }
        // test addLast
        StdOut.println("test addLast");
        intDeque.addLast(3);
        intDeque.addLast(4);
        intDeque.addLast(5);
        for (Integer i : intDeque) {
            StdOut.println(i);
        }
        // test removeFirst
        StdOut.println("test removeFirst");
        StdOut.println("removeFirst: " + intDeque.removeFirst());
        StdOut.println("removeFirst: " + intDeque.removeFirst());
        for (Integer i : intDeque) {
            StdOut.println(i);
        }
        StdOut.println("intDeque.size: " + intDeque.size());
        // test removeLast
        StdOut.println("test removeLast");
        StdOut.println("removeLast: " + intDeque.removeLast());
        StdOut.println("removeLast: " + intDeque.removeLast());
        for (Integer i : intDeque) {
            StdOut.println(i);
        }
        intDeque.removeLast();
        StdOut.println("is intDeque empty? : " + intDeque.isEmpty());
    }

}
