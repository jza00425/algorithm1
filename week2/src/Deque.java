import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N; // number of elements in Deque
    private Node pre; // sentinel before first item
    private Node post; // sentinel after last item

    // linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        N = 0;
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be null!");
        Node first = pre.next;
        Node x = new Node();
        x.item = item;
        x.prev = pre;
        x.next = first;
        pre.next = x;
        first.prev = x;
        N++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be null!");
        Node last = post.prev;
        Node x = new Node();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        N++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Stack underflow");
        Node first = pre.next;
        Node second = first.next;
        Item item = first.item;
        pre.next = first.next;
        second.prev = pre;
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Stack underflow");
        Node last = post.prev;
        Node secondLast = last.prev;
        Item item = last.item;
        post.prev = last.prev;
        secondLast.next = post;
        N--;
        return item;
    }

    // private String show() {
    // StringBuilder s = new StringBuilder();
    // for (Item item: this) {
    // s.append(item + " ");
    // for (Item item1: this) {
    // s.append(item1 + " ");
    // }
    // }
    // return s.toString();
    // }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = pre.next;
        private int index = 0;

        public boolean hasNext() {
            return index < N;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("reach the end of deque");
            Item item = current.item;
            current = current.next;
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        Deque<Integer> s = new Deque<Integer>();
        while (true) {
            int item = StdIn.readInt();
            if (item == 99)
                break;
            s.addLast(item);

        }
        // StdOut.println(s.show());

        // while (!s.isEmpty()) {
        // int p = s.removeLast();
        // StdOut.println(p);
        // }
        // s.removeFirst();

    }

}
