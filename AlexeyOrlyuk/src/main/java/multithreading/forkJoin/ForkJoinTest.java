package multithreading.forkJoin;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by Anna on 15.09.2016.
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++){
            numbers[i] = Math.random();
        }

        long startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        int poolResult = pool.invoke(
                new Counter(pool, numbers, 0, numbers.length, x -> x > 0.5)
        );

        System.out.printf("Result: %d.\n", poolResult);
        System.out.printf("Counting time: %d ms.\n", (System.currentTimeMillis() - startTime));
    }

}
