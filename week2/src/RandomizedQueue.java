import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int N = 0;

    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("item cannot be null!");
        if (N == s.length)
            resize(2 * s.length);
        s[N++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Stack underflow");
        StdRandom.shuffle(s, 0, N - 1);
        Item item = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length / 4)
            resize(s.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Stack underflow");
        int t = StdRandom.uniform(N);
        return s[t];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private String show() {
        StringBuilder str = new StringBuilder();
        for (Item item : this) {
            str.append(item + " ");
            for (Item item1 : this) {
                str.append(item1 + " ");
            }
        }
        return str.toString();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public ArrayIterator() {
            StdRandom.shuffle(s, 0, N - 1);
        }

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = s[i];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        RandomizedQueue<String> sop = new RandomizedQueue<String>();
        while (true) {
            String item = StdIn.readString();
            sop.enqueue(item);
            if (StdIn.isEmpty())
                break;
        }
        // StdOut.println(sop.show());
        while (!sop.isEmpty()) {
            String p = sop.dequeue();
            StdOut.println(p);
        }
    }

}
