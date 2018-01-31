package multithreading.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Recursive Counter, which uses Fork-Join multithreading
 * pattern to insure counting process speed.
 *
 * @author Anna
 * @author alex323glo
 * @version 1.0
 */
public class Counter extends RecursiveTask<Integer> {

    public static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private Filter filter;

    private ForkJoinPool pool;

    public Counter(ForkJoinPool pool, double[] values, int from, int to, Filter filter) {
        this.pool = pool;

        this.values = values;
        this.filter = filter;
        this.to = to;
        this.from = from;
    }

    @Override
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

            int result = 0;
            result += pool.invoke(new Counter(pool, values, from, mid, filter));
            result += pool.invoke(new Counter(pool, values, mid, to,  filter));

            return result;
        }
    }
}
