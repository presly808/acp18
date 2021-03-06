package multithreading.mathCounting;

import org.junit.Test;
import org.junit.Assert;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class MatchCounterTest {
    @Test
    public void matchCounter() throws Exception {
        int count = 0;
        String directory = ".";
        String keyword = "findThisText13579";

        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> submit = service.submit(new MatchCounter(new File(directory), keyword, service));
        try {
            count = submit.get();
            System.out.println(count + " matching files.");
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, count);
    }

}