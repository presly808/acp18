package db.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Data Base utils container (DAO).
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see DBUtils
 */
public class DBUtilsImpl implements DBUtils {

    private String url;
    private String user;
    private String password;

    public DBUtilsImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Creates new table in DB.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return true, if operation was successful.
     */
    @Override
    public boolean createTable(Class tableClass) {
        if (tableClass == null) {
            return false;
        }

        try (Statement statement = DriverManager
                .getConnection(url, user, password)
                .createStatement();
        ) {

            return !statement.execute(ReflectionUtils.createSQLTableStructure(tableClass));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Drops (deletes) table from DB.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return true, if operation was successful.
     */
    @Override
    public boolean dropTable(Class tableClass) {
        if (tableClass == null) {
            return false;
        }

        try (Statement statement = DriverManager
                .getConnection(url, user, password)
                .createStatement()
        ) {

            return !statement.execute("DROP TABLE " + ReflectionUtils.createTableName(tableClass) + ";");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Removes all records from table.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return true, if operation was successful.
     */
    @Override
    public boolean removeAllValues(Class tableClass) {
        if (tableClass == null) {
            return false;
        }

        try (Statement statement = DriverManager
                .getConnection(url, user, password)
                .createStatement()
        ) {

            return !statement.execute("DELETE FROM " + ReflectionUtils.createTableName(tableClass) + ";");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Executes native SQL query in current DB.
     *
     * @param sql String SQL query.
     * @return String result of query execution.
     */
    @Override
    public String nativeSQL(String sql) {
        if (sql == null) {
            return null;
        }

        try (Statement statement = DriverManager
                .getConnection(url, user, password)
                .createStatement()
        ) {

            statement.execute(sql);
            return Converter.resultSetToString(statement.getResultSet());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all records of table.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return List of records (tableClass objects).
     */
    @Override
    public <T> List<T> getAllValues(Class<T> tableClass) {
        // TODO complete
        throw new UnsupportedOperationException();
    }

    /**
     * Selects records from table using filters.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @param filters    Map of field filters.
     * @param orderBy    target field, which is used for ORDER BY SQL operation.
     * @param limit      value, used for LIMIT SQL operation.
     * @return result List of records (tableClass objects).
     */
    @Override
    public <T> List<T> selectWithFilter(Class<T> tableClass, Map<Field, Object> filters, Field orderBy, int limit) {
        // TODO complete
        throw new UnsupportedOperationException();
    }

    /**
     * Adds new record to table.
     *
     * @param tableClass  type, which describes table records as objects (used to extract table name).
     * @param tableRecord tableClass object, which data will be added to table as new record.
     * @return tableClass object of added record, if operation was successful, or null, if it wasn't.
     */
    @Override
    public <T> T add(Class<T> tableClass, T tableRecord) {
        if (tableClass == null || tableRecord == null) {
            return null;
        }

        try (Statement statement = DriverManager
                .getConnection(url, user, password)
                .createStatement()
        ) {

            boolean executionResult = statement.execute(
                    "INSERT INTO " + ReflectionUtils.createTableName(tableClass) +
                            " (" + ReflectionUtils.createTableHead(tableClass) + ") " +
                            "VALUES (" + ReflectionUtils.createTableRecord(tableClass, tableRecord) + ");");

            return executionResult ? null : tableRecord;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param tableClass  type, which describes table records as objects (used to extract table name).
     * @param tableRecord tableClass object, which data will be used to identify record,
     *                    needed to be removed from table.
     * @return tableClass object of removed record, if operation was successful, or null, if it wasn't.
     */
    @Override
    public <T> T remove(Class<T> tableClass, T tableRecord) {
        if (tableClass == null || tableRecord == null) {
            return null;
        }

        try (Statement statement = DriverManager
                .getConnection(url, user, password)
                .createStatement()
        ) {
            String tableName = ReflectionUtils.createTableName(tableClass);
            String matcher = ReflectionUtils.createSmartIdMatcherOfRecord(tableClass, tableRecord);

            try(ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE " + matcher + ";"
            )) {
                if (resultSet.first()) {
                    ReflectionUtils.recordToObject(tableClass, resultSet);
                }
            }

            boolean result = statement.execute(
                    "DELETE FROM " + tableName + " " + "WHERE " + matcher + ";");
            return result ? null : tableRecord;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}