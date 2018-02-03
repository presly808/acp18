package multithreading.mathCounting;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounterTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory:");
        String directory = in.nextLine();
        System.out.print("Enter keyword:");
        String keyword = in.nextLine();

        ExecutorService pool = Executors.newCachedThreadPool();
        MatchCounter matchCounter = new MatchCounter(new File(directory), keyword, pool);
        Future<Integer> result = pool.submit(matchCounter);
        try {
            System.out.println(result.get() + " matching files.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}
