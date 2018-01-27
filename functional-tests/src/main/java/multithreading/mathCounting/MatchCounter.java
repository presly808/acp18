package multithreading.mathCounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Anna on 14.09.2016.
 */
public class MatchCounter {

    private File directory;
    private String keyword;
    private int count;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    public int find(){
        count = 0;
        File[] files = directory.listFiles();
        for (File file : files){
            if (file.isDirectory()){
                MatchCounter counter = new MatchCounter(file, keyword);
                count += counter.find();
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
}
