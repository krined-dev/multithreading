import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.IntStream;


class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        ExecutorService executor = Executors.newFixedThreadPool(10);
        int numToFind = 1000000;
        int[] intArr = IntStream.range(1, 1000000).toArray();
        Integer index = null; 
        String threadID = null;
        int threadsAndNumbers = 10;
        
        
        for (int i = 0; i < threadsAndNumbers; i++) {
            intArr[(int)(Math.random() * (intArr.length - 1))] = numToFind;
        }
        Future<Integer> future = null;
        for (int i = 0; i < threadsAndNumbers; i++) {
             future = executor.submit(new intFinder(numToFind, intArr, i * (intArr.length / threadsAndNumbers),
                             (i+1) * (intArr.length / threadsAndNumbers))); 
            
        }
        while (!future.isDone()) {
            index = future.get();
            if (index != null) {
                future.cancel(true);
                threadID = Thread.currentThread().getName();
            }
        }

        System.out.println(index);
        

    }

    
}

class intFinder implements Callable<Integer> {
    private int numToFind;
    private int[] arr;
    private int startIndex;
    private int endIndex;
    
    public intFinder(int numToFind, int[] arr, int startIndex, int endIndex) {
        this.numToFind = numToFind;
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        
    }
    
    @Override
    public Integer call() {
        try {
            for (int i = this.startIndex; i <= this.endIndex; i++) {
                Thread.sleep(0);
                if (this.arr[i] == this.numToFind) {
                    // System.out.printf("%d at index: %d\n", this.numToFind, i);
                    return i;
                }
            }

            return null;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }
}