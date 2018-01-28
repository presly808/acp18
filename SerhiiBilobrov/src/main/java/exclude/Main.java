package exclude;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by serhii on 27.01.18.
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        executor.submit(() -> {
            // while a queue contains tasks
            // get new task from a queue
            // perform taken task
        });
        executor.shutdown();

        while (!executor.isTerminated()){
            try {
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
