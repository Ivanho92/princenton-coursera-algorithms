package week1_HelloWorld;

import edu.princeton.cs.algs4.StdOut;

public class HelloGoodbye {
    public static void main(String[] args) {
        var helloString = String.format("Hello %s and %s.", args[0], args[1]);
        var goodbyeString = String.format("Goodbye %s and %s.", args[1], args[0]);

        StdOut.println(helloString);
        StdOut.println(goodbyeString);
    }
}
