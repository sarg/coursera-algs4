import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        int i = 1;
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (i <= k) {
                rq.enqueue(str);
            } else {
                int j = StdRandom.uniform(1, i+1);
                if (j <= k) {
                    rq.dequeue();
                    rq.enqueue(str);
                }
            }
            i++;
        }

        for (String s : rq) {
            StdOut.println(s);
        }
    }
}