package multithreading.mathCounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounter implements Callable{

    private File directory;
    private String keyword;
    private int count;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    public int find(){
        int countThread = MatchCounterTest.getCountsThread();
        System.out.println("Thread №" + countThread);
        count = 0;
        File[] files = directory.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                Future counter = MatchCounterTest.service.submit(new MatchCounter(file, keyword));
                /*MatchCounter counter = new MatchCounter(file, keyword);*/
                try {
                    count += (int) counter.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            else {
                if (search(file)) {count++;}
            }
        }
        return count;
    }

    public boolean search(File file){
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

    @Override
    public Object call() throws Exception {
        return find();
    }
}
