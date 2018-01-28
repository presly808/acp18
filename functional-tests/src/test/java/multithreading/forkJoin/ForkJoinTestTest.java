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
        Counter counter = new Counter(numbers, 0, numbers.length, new Filter(){
            @Override
            public boolean accept(double x) {
                return x < 5_000_000;
            }
        });

        ForkJoinPool pool = new ForkJoinPool();
        assertThat(counter.compute(), CoreMatchers.equalTo(5_000_000));

    }
}