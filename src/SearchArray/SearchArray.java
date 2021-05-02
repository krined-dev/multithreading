package SearchArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SearchArray {
    private int index = -1;
    private int findingThread = -1;
    private int numToFind = 1000000;
    private int threadCount = 10;
    private int[] intArr = new int[numToFind];
    private int[] startIndices = new int[threadCount];
    private int[] endIndicies = new int[threadCount];
    private ExecutorService pool = Executors.newCachedThreadPool();
    private boolean running = true;

    public static void main(String[] args) {
        SearchArray search = new SearchArray();;
    }
    public SearchArray() {
       init();
       runThreads();

       System.out.printf("%d found on index %d on thread %d", numToFind, index, findingThread);
    }
    private void init() {
        intArr = IntStream.range(1, numToFind).toArray();
        for (int i = 0; i < threadCount; i++) {
            startIndices[i] = (numToFind / threadCount) * i;
            endIndicies[i] = ((numToFind / threadCount) * (i+1)) - 1;
            int randomIndex = (int)(Math.random()*numToFind);
            //System.out.println(randomIndex);
            intArr[randomIndex] = numToFind;
        }
    }

    private void runThreads() {
        try {
            for (int i = 0; i < threadCount && !pool.isShutdown(); i++) {
                ArraySearchTask thread = new ArraySearchTask(startIndices[i], endIndicies[i], i, numToFind);
                pool.execute(thread);
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception ignore) {}
    }

    private synchronized void numberFound(int foundIndex, int threadNumber) {
        this.index = foundIndex;
        this.findingThread = threadNumber;
        this.running = false;
    }
    private class ArraySearchTask implements Runnable {
        int startIndex;
        int endIndex;
        int threadNumber;
        int numToFind;

        ArraySearchTask(int startIndex, int endIndex, int threadNumber, int numToFind) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadNumber = threadNumber;
            this.numToFind = numToFind;
        }

        @Override
        public void run() {
            int i = startIndex;
            do {
                if (numToFind == intArr[i]) {
                    numberFound(i, threadNumber);
                }
                if (i % 1000 == 0) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i++;
            } while (running && i <= endIndex);
        }
    }
}
