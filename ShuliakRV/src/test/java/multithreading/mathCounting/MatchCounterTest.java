package multithreading.mathCounting;

import org.junit.Test;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class MatchCounterTest {

    @Test
    public void MatchCounter () {
        //Scanner in = new Scanner(System.in);
        //System.out.print("Enter base directory:");
        String directory = "."; //in.nextLine();
        //System.out.print("Enter keyword:");
        String keyword = "java"; //in.nextLine();

        ExecutorService pool = Executors.newCachedThreadPool();
        MatchCounter matchCounter = new MatchCounter(new File(directory), keyword, pool);
        Future<Integer> result = pool.submit(matchCounter);
        try {
            assertTrue(result.get()>=0 );
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }


}