package com.gmail.legamemc.adventofcode2022;

import com.gmail.legamemc.adventofcode2022.questions.Day1;

public class Program {
    private final static int EXECUTE_TEST_COUNT = 20000;
    public static void main(String[] args) throws Exception {
        Challenge<?> challenge = new Day1();

        Object result = challenge.execute();

        System.out.println("Result: " + result);

        System.out.println("Calculating average execute time...");

        long time = 0;

        for(int x = 0; x < EXECUTE_TEST_COUNT; x++){
            long timeTaken = calculateExecuteTime(() -> {
                try {
                    challenge.execute();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("Time taken at #" + x + ": " + (timeTaken/1000000.0) + "ms");
            time += timeTaken;
        }

        System.out.format("\nAverage execute time: %.4fms\n", ((time / (double) EXECUTE_TEST_COUNT) /1000000.0));

    }

    private static long calculateExecuteTime(Runnable runnable){
        long t1 = System.nanoTime();
        runnable.run();
        long t2 = System.nanoTime();

        return t2 - t1;
    }
}
