package multithreading.mathCounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounter extends SearchContainer implements Runnable{

    private int count;
    ExecutorService service;
    List<MatchCounter> tasks;

    public MatchCounter(File directory, String keyword, ExecutorService service) {
        super(directory, keyword);
        this.service = service;
        tasks = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public int find() throws InterruptedException, ExecutionException {

        if (directory == null) {
            return 0;
        }

        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            return 0;
        }

        count = 0;
        for (File file : files){
            if (file.isDirectory()){

                MatchCounter task = new MatchCounter(file, keyword, service);
                Thread thread = new Thread(task);

                tasks.add(task);

                thread.start();
                thread.join();

            } else {
                Future<Boolean> future = service.submit(new AsyncSearch(file,keyword));
                if (future.get()) {count++;}
            }
        }

        for (MatchCounter task : tasks) {
            count += task.getCount();
        }

        return count;
    }

    @Override
    public void run() {
        try {
            this.count = find();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AsyncSearch extends SearchContainer implements Callable<Boolean>{

        public AsyncSearch(File directory, String keyword) {
            super(directory, keyword);
        }

        @Override
        public Boolean call() throws Exception {
            File file = directory;
            try (Scanner in = new Scanner(file)){
                boolean found = false;
                while (!found && in.hasNextLine()){
                    String line  = in.nextLine();
                    if (line.contains(keyword)) {found = true;}
                }
                return found;
            } catch (FileNotFoundException e) {
                return false;
            }
        }
    }
}




