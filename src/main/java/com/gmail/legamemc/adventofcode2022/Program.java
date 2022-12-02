package com.gmail.legamemc.adventofcode2022;

import com.gmail.legamemc.adventofcode2022.questions.Day1;

public class Program {

    public static void main(String[] args) throws Exception {
        Challenge challenge = new Day1();
        long t1 = System.nanoTime();
        challenge.execute();
        long t2 = System.nanoTime();
        System.out.format("\n\nTime taken: %.4fms", ((t2-t1)/1000000.0));
    }
}
