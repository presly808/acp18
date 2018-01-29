package multithreading.mathCounting;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounterTest {

    public static ExecutorService service;
    public static volatile int countThreads;

    public static int getCountsThread(){
        return ++countThreads;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory:");
        String directory = in.nextLine();
        System.out.print("Enter keyword:");
        String keyword = in.nextLine();

        service = Executors.newCachedThreadPool();
        Future submit = service.submit(new MatchCounter(new File(directory), keyword));
        /*MatchCounter matchCounter = new MatchCounter(new File(directory), keyword);*/
        try {
            System.out.println((int) submit.get() + " matching files.");
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
