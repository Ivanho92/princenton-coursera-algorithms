package week1_HelloWorld;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        var champion = "";
        var index = 1;

        while (!StdIn.isEmpty()) {
            var input = StdIn.readString();

            if (index == 1) {
                champion = input;
            }

            var probability = 1.0 / index;
            if (StdRandom.bernoulli(probability)) {
                champion = input;
            }

            index++;
        }

        StdOut.println(champion);
    }
}
