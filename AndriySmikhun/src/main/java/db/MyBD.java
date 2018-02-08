package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyBD {
    public static void main(String[] args) {


    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:/home/andriy/IdeaProjects/acp18/AndriySmikhun/src/main/java/db/MyDB.bd";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new databases been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}