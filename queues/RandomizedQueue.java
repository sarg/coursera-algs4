import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[2];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    private void resize(int max) {
        Item[] olditems = items;
        items = (Item[]) new Object[max];
        for (int i = 0; i < size; i++) {
            items[i] = olditems[i];
        }
    }

    private void swap(int i, int j) {
        Item t = items[i];
        items[i] = items[j];
        items[j] = t;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (size == items.length) resize(size * 2);
        items[size] = item;
        swap(size, StdRandom.uniform(size + 1));
        ++size;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
/*
        int i = StdRandom.uniform(size);
        swap(i, size-1);
*/

        Item item = items[--size];
        items[size] = null;
        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return items[StdRandom.uniform(size)];
    }

    private class QIterator implements Iterator<Item> {
        private int[] shuffle;
        private int cur;

        public QIterator() {
            shuffle = new int[size];
            for (int i = 0; i < size; i++) {
                shuffle[i] = i;
            }

            cur = 0;
            StdRandom.shuffle(shuffle);
        }

        @Override
        public boolean hasNext() {
            return cur < shuffle.length;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return items[shuffle[cur++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QIterator();
    }

    // unit testing
    public static void main(String[] args) {
    }

}