/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int capacity;
    private int size;
    private int head;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 2;
        size = 0;
        head = 0;
        tail = 0;
        items = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        items[tail] = item;
        tail = (tail + 1) % capacity;
        size++;
        if (size == capacity) {
            resize(2 * capacity);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("");
        }
        int bias = StdRandom.uniformInt(size);
        Item item = items[(head + bias) % capacity];
        items[(head + bias) % capacity] = items[head];
        items[head] = null;
        head = (head + 1) % capacity;
        size--;
        if (size * 4 == capacity && size >= 2) {
            resize(capacity / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("");
        }
        int bias = StdRandom.uniformInt(size);
        return items[(head + bias) % capacity];
    }

    private void resize(int newCapacity) {
        Item[] oldItems = items;
        items = (Item[]) new Object[newCapacity];
        int index;
        for (index = 0; index < size; index++) {
            items[index] = oldItems[(head + index) % capacity];
        }
        head = 0;
        tail = index;
        capacity = newCapacity;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandQueIterator();
    }

    private class RandQueIterator implements Iterator<Item> {
        private Item[] recordTable;
        private int index;
        private int visited;

        private RandQueIterator() {
            recordTable = (Item[]) new Object[size];
            int i;
            for (i = 0; i < size; i++) {
                recordTable[i] = items[(head + i) % capacity];
            }
            StdRandom.shuffle(recordTable);

        }

        public boolean hasNext() {
            return index < recordTable.length;
        }

        public void remove() {
            throw new UnsupportedOperationException("");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("");
            }
            return recordTable[index++];
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        // create an empty RandomizeQueue
        RandomizedQueue<Integer> intRandQue = new RandomizedQueue<>();
        RandomizedQueue<String> stringRandQue = new RandomizedQueue<>();
        StdOut.println(intRandQue.isEmpty());
        StdOut.println(stringRandQue.isEmpty());
        StdOut.println(intRandQue.size());
        for (String s : stringRandQue) {
            StdOut.println(s);
        }
        // test Integer
        // test enqueue
        StdOut.println("test enqueue");
        for (int i = 0; i < 7; i++) {
            intRandQue.enqueue(i);
        }
        // test size
        StdOut.println("size: " + intRandQue.size());
        StdOut.println("test iterator");
        for (Integer i : intRandQue) {
            StdOut.println(i);
        }
        StdOut.println("test iterator independence");
        for (Integer i : intRandQue) {
            StdOut.println(i);
        }
        // test sample
        StdOut.println("test sample");
        StdOut.println(intRandQue.sample());
        StdOut.println(intRandQue.sample());
        StdOut.println(intRandQue.sample());
        // test dequeue
        StdOut.println("test dequeue");
        StdOut.println(intRandQue.dequeue());
        StdOut.println(intRandQue.dequeue());
        StdOut.println(intRandQue.dequeue());
        // after dequeue
        StdOut.println("after dequeue");
        StdOut.println("size: " + intRandQue.size());
        for (Integer i : intRandQue) {
            StdOut.println(i);
        }
        StdOut.println("Failed tests online");
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(35);
        // StdOut.println(queue.size());
        // StdOut.println(queue.iterator());
        // StdOut.println(queue.size());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
    }
}
