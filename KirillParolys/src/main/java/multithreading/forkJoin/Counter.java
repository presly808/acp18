package multithreading.forkJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Counter extends RecursiveTask<Integer> {

    public static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private Filter filter;
//    private Runnable runnable;
//    private Callable callable;

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
            return  ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        }

    }

    protected List<Counter> createSubtasks() {
        List<Counter> subtasks = new ArrayList<>();

        double[] firstPart = Arrays.copyOfRange(values, 0, values.length / 2);
        double[] secondPart = Arrays.copyOfRange(values, values.length / 2, values.length);

        subtasks.add(new Counter(firstPart, 0, firstPart.length, filter));
        subtasks.add(new Counter(secondPart, 0, secondPart.length, filter));

        return subtasks;
    }

}
