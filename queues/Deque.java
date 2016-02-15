import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Node next;
        private Node prev;
        private Item value;

        public Node(Node next, Node prev, Item value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();

        size++;
        Node oldhead = head;
        head = new Node(oldhead, null, item);
        if (oldhead != null) oldhead.prev = head;

        if (tail == null)
            tail = head;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();

        size++;
        Node oldtail = tail;
        tail = new Node(null, oldtail, item);
        if (oldtail != null) oldtail.next = tail;

        if (head == null)
            head = tail;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        size--;
        Item ret = head.value;
        head = head.next;

        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }
        return ret;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        size--;
        Item ret = tail.value;
        tail = tail.prev;

        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }
        return ret;
    }

    public Iterator<Item> iterator() {
        return new DIterator();
    }

    public static void main(String[] args) {
    }

    private class DIterator implements Iterator<Item> {
        private Node cur = head;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();

            Item r = cur.value;
            cur = cur.next;
            return r;
        }
    }
}
