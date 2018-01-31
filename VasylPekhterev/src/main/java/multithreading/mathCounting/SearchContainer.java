package multithreading.mathCounting;

import java.io.File;

public class SearchContainer {
    protected File directory;
    protected String keyword;

    public SearchContainer(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }
}