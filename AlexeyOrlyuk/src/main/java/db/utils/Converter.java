package db.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Reflection util methods container.
 *
 * @author alex323glo
 * @version 1.0
 */
public class Converter {

    /**
     * Generates new instance of class from String.
     *
     * @param objectType target class.
     * @param stringObject target String version of new class instance.
     * @return result new instance, if operation was successful, or null, if it wasn't.
     */
    public static<T> T stringToObject(Class<T> objectType, String stringObject) {
        if (objectType == null || stringObject == null) {
            return null;
        }

        if (objectType.isPrimitive()) {
            switch (objectType.getSimpleName()) {
                case "int":
                    return (T) Integer.valueOf(stringObject);
                case "double":
                    return (T) Double.valueOf(stringObject);
                case "long":
                    return (T) Long.valueOf(stringObject);
            }
        }

        switch (objectType.getName()) {
            case "java.lang.String":
                return (T) stringObject;
            case "java.lang.Integer":
                return (T) Integer.valueOf(stringObject);
            case "java.lang.Double":
                return (T) Double.valueOf(stringObject);
            case "java.lang.Long":
                return (T) Long.valueOf(stringObject);
        }

        Constructor[] constructors = objectType.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() != 1) {
                continue;
            }

            T fountObject = tryToUseConstructorWithString(constructor, stringObject);
            if (fountObject != null) {
                return fountObject;
            }
        }

        return null;
    }

    /**
     * Converts java type (Class object) to SQL type (as String).
     *
     * @param type target java type.
     * @return result SQL type (as String).
     */
    public static String convertToSQLType(Class type) {
        if (type == null) {
            return null;
        }

        if (type.isPrimitive()) {
            switch (type.getSimpleName()) {
                case "int":
                    return "INT";
                case "double":
                    return "DOUBLE";
                // TODO add more types and remove hardcode
                default:
                    return "VARCHAR(255)";
            }
        }

        switch (type.getSimpleName()) {
            case "String":
                return "VARCHAR(255)";
            // TODO add more types and remove hardcode
            default:
                return "REF";
        }
    }

    /**
     * Converts SQL type (String) to java type (as Class object).
     *
     * @param sqlType target SQL type.
     * @return result SQL type as (Class object).
     */
    public static Class convertSQLType(String sqlType) {
        if (sqlType == null) {
            return null;
        }

        switch (sqlType.toUpperCase()) {
            case "INT":
                return int.class;
            case "DOUBLE":
                return double.class;
            case "VARCHAR":
                return String.class;
                // TODO add more types and remove hardcode
            default:
                if (sqlType.toUpperCase().contains("VARCHAR")) {
                    return String.class;
                }
                return Object.class;
        }
    }

    public static Map<String, String> resultSetToMap(ResultSet resultSet) throws SQLException {
        Map<String, String> recordTypeValueMap = new TreeMap<>();

        if (resultSet.isClosed()) {
            return null;
        }

        ResultSetMetaData metaData = resultSet.getMetaData();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            recordTypeValueMap.put(metaData.getColumnName(i), resultSet.getString(i));
        }

        return recordTypeValueMap;
    }

    /**
     * Creates String representation of ResultSet object.
     *
     * @param resultSet target ResultSet object.
     * @return result String, or null (if operation is not successful), or empty String
     * (if ResultSet is empty).
     * @throws SQLException if has some problems during work with ResultSet object.
     */
    public static String resultSetToString(ResultSet resultSet) throws SQLException {
        if (resultSet == null || resultSet.isClosed()) {
            return null;
        }

        if (!resultSet.first()) {
            return "";
        }

        StringBuilder resultBuilder = new StringBuilder();

        do {
            resultBuilder.append(stringifyResultSetRow(resultSet));
            resultBuilder.append("\n");
        } while (resultSet.next());

        return resultBuilder.deleteCharAt(resultBuilder.length() - 1).toString();
    }

    /**
     * Uses proposed constructor to create new instance.
     *
     * @param constructor proposed constructor.
     * @param stringObject constructor argument.
     * @return new instance, if operation was successful, or null, if it wasn't.
     */
    private static<T> T tryToUseConstructorWithString(Constructor constructor, String stringObject) {
        for (Class parameterType: constructor.getParameterTypes()) {
            if (parameterType.equals(String.class)) {
                try {
                    return (T) constructor.newInstance(stringObject);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Converts current ResultSet row (record) to StringBuilder.
     *
     * @param resultSet target  ResultSet.
     * @return result StringBuilder object.
     * @throws SQLException if has some problems during work with ResultSet object.
     */
    private static StringBuilder stringifyResultSetRow(ResultSet resultSet) throws SQLException {
        StringBuilder row = new StringBuilder();

        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            row.append(resultSet.getString(i));
            if (i != resultSet.getMetaData().getColumnCount()) {
                row.append(" ");
            }
        }

        return row;
    }

}
