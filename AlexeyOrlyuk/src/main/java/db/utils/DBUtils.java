package db.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Data Base utils container (DAO).
 *
 * @author alex323glo
 * @version 1.0
 */
public interface DBUtils {

    /**
     * Creates new table in DB.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return true, if operation was successful.
     */
    boolean createTable(Class tableClass);

    /**
     * Drops (deletes) table from DB.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return true, if operation was successful.
     */
    boolean dropTable(Class tableClass);

    /**
     * Removes all records from table.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return true, if operation was successful.
     */
    boolean removeAllValues(Class tableClass);

    /**
     * Executes native SQL query in current DB.
     *
     * @param sql String SQL query.
     * @return String result of query execution.
     */
    String nativeSQL(String sql);

    /**
     * Gets all records of table.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @return List of records (tableClass objects).
     */
    <T> List<T> getAllValues(Class<T> tableClass);

    /**
     * Selects records from table using filters.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @param filters Map of field filters.
     * @param orderBy target field, which is used for ORDER BY SQL operation.
     * @param limit value, used for LIMIT SQL operation.
     * @return result List of records (tableClass objects).
     */
    <T> List<T> selectWithFilter(Class<T> tableClass, Map<Field,Object> filters, Field orderBy, int limit);

    /**
     * Adds new record to table.
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @param tableRecord tableClass object, which data will be added to table as new record.
     * @return tableClass object of added record, if operation was successful, or null, if it wasn't.
     */
    <T> T add(Class<T> tableClass, T tableRecord);

    /**
     *
     * @param tableClass type, which describes table records as objects (used to extract table name).
     * @param tableRecord tableClass object, which data will be used to identify record,
     *                    needed to be removed from table.
     * @return tableClass object of removed record, if operation was successful, or null, if it wasn't.
     */
    <T> T remove(Class<T> tableClass, T tableRecord);

}
