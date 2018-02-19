package db;

import db.model.Base;
import db.model.User;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyBD {
    public static void main(String[] args) {

        String url = "jdbc:sqlite:/home/andriy/IdeaProjects/acp18/AndriySmikhun/src/main/java/hibernate/database.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

       //DBUtils dbutils = new DBUtils(url);

       // System.out.println("Create Table" + dbutils.createTable(User.class));
       // System.out.println("Drop Table" + dbutils.dropTable(User.class));
    }
}