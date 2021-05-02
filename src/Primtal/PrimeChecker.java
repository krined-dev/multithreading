package Primtal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrimeChecker {
    private ExecutorService pool = Executors.newCachedThreadPool();
    private boolean isPrime = true;
    private long number = 9223372036854775783L;
    private final int numberOfThreads = 10;
    private long[] startValues = new long[numberOfThreads];
    private long[] endValues = new long[numberOfThreads];
    private long startTime;


    public static void main(String[] args) {
        PrimeChecker check = new PrimeChecker();
    }

    public PrimeChecker() {
        init();
        runThreads();
    }

    private void runThreads() {
        startTime = System.currentTimeMillis();
        try {
            for (int i = 0; i < numberOfThreads ; i++) {
                PrimeTask task = new PrimeTask(number, startValues[i], endValues[i]);
                pool.execute(task);
            }
        } catch(Exception ignore) {}
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch(Exception ignore) {}
        System.out.println("Prime:" + isPrime + " time: "  + (System.currentTimeMillis() - startTime) + "ms");
    }


    private void init() {
        for (int i = 0; i < numberOfThreads; i++) {
            startValues[i] = i * ((long)(Math.sqrt(number)) / numberOfThreads);
            endValues[i] = (i + 1) * ((long)Math.sqrt(number) / numberOfThreads);
        }
        startValues[0] = 3;
    }




private class PrimeTask implements Runnable {
    private long number;
    private long startValue;
    private long endValue;

    PrimeTask(long number, long startValue, long endValue) {
        this.number = number;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    public void run() {
        if (number % 2 == 0) {
            isPrime = false;
        }
        long i = startValue;
        if (i % 2 == 0) {
            i--;
        }
        do {
            if (number % i == 0) {
                isPrime = false;
            }
            i += 2;
        } while (isPrime && i <= endValue);

    }
}
}
