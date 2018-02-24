package videos;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _02Thread {

    public static void main(String[] args) {
        File folder = new File("C:\\Users\\usr\\IdeaProjects\\acp18\\ArtemShchepets");

        File[] files = folder.listFiles();

        List<Thread> threads = new ArrayList<>();
        List<AsynchTask> tasks = new ArrayList<>();
        List<File> results = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                AsynchTask task = new AsynchTask(file,"Test");
               Thread thread = new Thread(task);

               threads.add(thread);
               //tasks.add(task);

               thread.start();
            }
        }

        for (Thread thread : threads) {
            try {
                threads.get(0).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (AsynchTask task : tasks) {
            List<File> result = task.getRes();
            results.addAll(result);
        }
        
        for (File result : results) {
            System.out.println(result.getAbsolutePath());
        }
    }

}


class AsynchTask implements Runnable {

    private File root;
    private List<File> searchResult;
    private String keyWord;


    public AsynchTask(File root, String keyWord) {
        this.root = root;
        this.keyWord = keyWord;
        searchResult = new ArrayList<>();
    }

    @Override
    public void run() {
        if (root == null) {
            return;
        }

        File[] files = root.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        Arrays.stream(files)
                .filter((file) -> file.getName().contains(keyWord))
                .forEach((file) -> searchResult.add(file));

    }

    public List<File> getRes() {
        return searchResult;
    }
}