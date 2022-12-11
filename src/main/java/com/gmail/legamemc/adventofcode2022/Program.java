package com.gmail.legamemc.adventofcode2022;

import com.gmail.legamemc.adventofcode2022.questions.*;


public class Program {
    private final static int EXECUTE_TEST_COUNT = 5;

    private final static boolean TEST_PERFORMANCE = false;
    private final static boolean SHOW_TIME_PER_EXECUTE = false;
    public static void main(String[] args) throws Exception {
        Challenge<?> challenge = new Day9();

        Object result = challenge.execute();

        System.out.println("Result: " + result);

        if(TEST_PERFORMANCE){
            System.out.println("\nCalculating average execute time...");
            long time = 0;

            for(int x = 0; x < EXECUTE_TEST_COUNT; x++){
                long timeTaken = calculateExecuteTime(() -> {
                    try {
                        challenge.execute();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                if(SHOW_TIME_PER_EXECUTE){
                    System.out.println("Time taken at #" + x + ": " + (timeTaken/1000000.0) + "ms (" + timeTaken + " nanoseconds)");
                }
                time += timeTaken;
            }

            System.out.format("\nTotal time taken for " + EXECUTE_TEST_COUNT + " test: %.4fms\n", ((time /1000000.0)));
            System.out.format("\nAverage execute time: %.4fms\n", ((time / (double) EXECUTE_TEST_COUNT) /1000000.0));
//            System.out.format("\nTotal time taken for " + EXECUTE_TEST_COUNT + " test: %dms\n", TimeUnit.NANOSECONDS.toMillis(time));
//            System.out.format("\nAverage execute time: %.4fms\n", (TimeUnit.NANOSECONDS.toMillis(time) / (double) EXECUTE_TEST_COUNT));
        }

    }

    private static long calculateExecuteTime(Runnable runnable){
        long t1 = System.nanoTime();
        runnable.run();
        long t2 = System.nanoTime();

        return t2 - t1;
    }
}
