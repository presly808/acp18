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
        Counter counter = new Counter(numbers, 0, numbers.length, new Filter(){
            @Override
            public boolean accept(double x) {
                return x > 0.5;
            }
        });

        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(counter.compute());

    }

}
