package db.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CSVReaderTest {

    private static final String TEST_CSV_FILE_PATH = "src/test/resources/test_csv_file.csv";
    private static final String TEST_CSV_FILE_CONTENT =
            "id,name,salary,age,department_id,city_id,man_id\n" +
                    "1,Ivan,2500,30,2,2,2\n" +
                    "2,Oleg,3500,33,2,2,-1\n" +
                    "3,Yura,1500,35,2,2,2\n" +
                    "4,Serhii,2500,22,1,2\n" +
                    "5,Olex,4500,24,1,1,2";

    private static List<List<String>> expectedList;

    @Before
    public void setUp() throws Exception {
        try (FileWriter writer = new FileWriter(new File(TEST_CSV_FILE_PATH))) {
            writer.write(TEST_CSV_FILE_CONTENT);
        } catch (IOException e) {
            throw new IOException(e);
        }

        expectedList = Arrays.stream(TEST_CSV_FILE_CONTENT.split("\n"))
                .skip(1)
                .map(row -> Arrays.asList(row.split(",")))
                .collect(Collectors.toList());
    }

    @After
    public void tearDown() throws Exception {
        File file = new File(TEST_CSV_FILE_PATH);
        if (!file.delete()) {
            throw new Exception("Can't delete file by path \"" + TEST_CSV_FILE_PATH + "\"");
        }
    }

    @Test
    public void read() throws Exception {
        assertEquals(expectedList, CSVReader.read(TEST_CSV_FILE_PATH));
    }

}