package multithreading.forkJoin;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Anna on 15.09.2016.
 */

public class Counter extends RecursiveTask<Integer>{

    public static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private Filter filter;

    public Counter(double[] values, int from, int to, Filter filter) {
        this.values = values;
        this.filter = filter;
        this.to = to;
        this.from = from;
    }

    protected Integer compute() {
        if (to - from < THRESHOLD) {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.accept(values[i])) {
                    count++;
                }
            }
            return count;
        } else {
            int mid = (from + to) / 2;
            ForkJoinTask<Integer> first = new Counter(values, from, mid, filter);
            ForkJoinTask<Integer> second = new Counter(values, mid, to, filter);

            first.fork();
            second.fork();

            Integer result = 0;
            result += second.join();
            result += first.join();


            return result;
        }
    }
}
