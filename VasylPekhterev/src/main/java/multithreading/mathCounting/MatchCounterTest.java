package multithreading.mathCounting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounterTest {

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter base directory:");
        String directory = in.nextLine();
        System.out.print("Enter keyword:");
        String keyword = in.nextLine();

        ExecutorService service = Executors.newFixedThreadPool(20);

        MatchCounter matchCounter = new MatchCounter(new File(directory), keyword, service);

        /*MatchCounter matchCounter = new MatchCounter(
                new File("D:\\Documents\\Programing\\Java\\JavaProjects\\acp18"), "test", service);*/

        Thread thread = new Thread(matchCounter);
        thread.start();
        thread.join();

        System.out.println(matchCounter.getCount() + " matching files.");
        service.shutdown();
    }
}
