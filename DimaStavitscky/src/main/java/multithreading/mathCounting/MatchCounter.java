package multithreading.mathCounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounter implements Callable<Integer>{

    private File directory;
    private String keyword;
    private int count;
    private ExecutorService service;

    public MatchCounter(File directory, String keyword, ExecutorService service) {
        this.directory = directory;
        this.keyword = keyword;
        this.service = service;
    }

    @Override
    public Integer call(){
        count = 0;
        File[] files = directory.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                Future<Integer> counter = service.submit(new MatchCounter(file, keyword, service));

                try {
                    count += counter.get();
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
                if (line.contains(keyword)) {
                    System.out.println(file.getPath());
                    found = true;
                }
            }
            return found;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
