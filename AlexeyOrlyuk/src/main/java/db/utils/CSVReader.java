package db.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV reader util container. Based on org.apache.commons.csv package instruments.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see org.apache.commons.csv;
 */
public class CSVReader {

    /**
     * Reads CSV file by path to List of Lists of Strings.
     *
     * @param path String path to target CSV file.
     * @return result List.
     *
     * @see List
     */
    public static List<List<String>> read(String path) {
        try (Reader reader = new FileReader(new File(path))) {

            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            return collectRecords(parser);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Collects result of CSV parsing to List of Lists of Strings.
     *
     * @param parser result of CSV parsing.
     * @return result List.
     *
     * @see CSVParser
     */
    private static List<List<String>> collectRecords(CSVParser parser) {
        List<List<String>> result = new ArrayList<>();

        for (CSVRecord record: parser) {
            List<String> row = new ArrayList<>();

            for (String part: record) {
                row.add(part);
            }
            result.add(row);
        }

        return result;
    }

}
