package multithreading.forkJoin;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;

/**
 * Created by serhii on 27.01.18.
 */
public class ForkJoinTestTest {

    @Test
    public void mainTest(){
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++){
            numbers[i] = i;
        }

        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(
                new Counter(pool, numbers, 0, numbers.length, x -> x < 5_000_000));

        assertThat(result, CoreMatchers.equalTo(5_000_000));

    }
}