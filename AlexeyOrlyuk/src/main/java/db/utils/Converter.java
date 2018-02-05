package db.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reflection util methods container.
 *
 * @author alex323glo
 * @version 1.0
 */
public class Converter {

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
