package multithreading.matchCounter;

import multithreading.matchCounting.MatchCounter;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// test

public class MatchCounterTest {
    static File dir;
    private static int counter = 0;

    @BeforeClass
    public static void createEnv() {

        dir = new File("./src/test/java/multithreading/matchCounter");

        for (File file : dir.listFiles()) {
            System.out.println(file.getName());
            if (file.getName().contains("Counter")) { counter++; }
        }

    }


    @Test
    public void mainTest() throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newCachedThreadPool();
        Future<Integer> result = pool.submit(new MatchCounter(dir, "Counter", pool)); // can actually put any keyword there
        assertEquals(((int) result.get()), counter);

    }
}
