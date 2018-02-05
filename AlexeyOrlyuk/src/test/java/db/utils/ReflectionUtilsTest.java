package db.utils;

import db.model.Base;
import db.model.City;
import db.model.Department;
import db.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    private static final String expectedBaseTable =
            "CREATE TABLE Base (\n" +
                    "id INT,\n" +
                    "name VARCHAR(255),\n" +
                    "PRIMARY KEY (id)\n" +
                    ");";

    private static final String expectedDepartmentTable =
            "CREATE TABLE Department (\n" +
                    "id INT,\n" +
                    "name VARCHAR(255),\n" +
                    "PRIMARY KEY (id)\n" +
                    ");";

    private static final String expectedCityTable =
            "CREATE TABLE City (\n" +
                    "id INT,\n" +
                    "name VARCHAR(255),\n" +
                    "PRIMARY KEY (id)\n" +
                    ");";

    private static final String expectedUserTable =
            "CREATE TABLE User (\n" +
            "id INT,\n" +
            "name VARCHAR(255),\n" +
            "age INT,\n" +
            "salary DOUBLE,\n" +
            "department_id INT,\n" +
            "city_id INT,\n" +
            "manage_id INT,\n" +
            "PRIMARY KEY (id)\n" +
            ");";

    @Test
    public void createTableName() throws Exception {
        assertEquals(Object.class.getSimpleName(), ReflectionUtils.createTableName(Object.class));
    }

    @Test
    public void createSQLTableStructureFromBase() throws Exception {
        assertEquals(expectedBaseTable, ReflectionUtils.createSQLTableStructure(Base.class));
    }

    @Test
    public void createSQLTableStructureFromDepartment() throws Exception {
        assertEquals(expectedDepartmentTable, ReflectionUtils.createSQLTableStructure(Department.class));
    }

    @Test
    public void createSQLTableStructureFromCity() throws Exception {
        assertEquals(expectedCityTable, ReflectionUtils.createSQLTableStructure(City.class));
    }

    @Test
    public void createSQLTableStructureFromUser() throws Exception {
        assertEquals(expectedUserTable, ReflectionUtils.createSQLTableStructure(User.class));
    }

}