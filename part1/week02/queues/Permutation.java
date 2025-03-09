/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> stringQueue = new RandomizedQueue<>();
        // while (!StdIn.isEmpty()) {
        //     stringQueue.enqueue(StdIn.readString());
        // }
        // for (int i = 0; i < k; i++) {
        //     StdOut.println(stringQueue.dequeue());
        // }
        int count = 1;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (count <= k) {
                stringQueue.enqueue(s);
            }
            else {
                if (StdRandom.bernoulli(k / (double) count)) {
                    stringQueue.dequeue();
                    stringQueue.enqueue(s);
                }
            }
            count++;
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(stringQueue.dequeue());
        }

    }
}
