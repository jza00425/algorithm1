public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> set = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            set.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(set.dequeue());
        }
    }

}
