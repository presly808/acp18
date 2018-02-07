package db.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reflection util methods container.
 *
 * @author alex323glo
 * @version 1.0
 */
public class ReflectionUtils {

    /**
     * Generates "CREATE TABLE" SQL query String. Uses tableClass
     * to get SQL Table structure info.
     *
     * @param tableClass class of table record, represents table structure.
     * @return generated query String.
     */
    public static String createSQLTableStructure(Class tableClass) {
        if (tableClass == null) {
            return null;
        }

        StringBuilder resultBuilder = new StringBuilder("CREATE TABLE ");

        resultBuilder.append(createTableName(tableClass)).append(" (\n");

        resultBuilder.append(makeTableFields(tableClass));

        resultBuilder.append(makeTableConstraints(tableClass));

        return resultBuilder.append(");").toString();
    }

    /**
     * Creates table name of tableClass simpleName.
     *
     * @param tableClass class, which simpleName is used as table name.
     * @return String table name.
     */
    public static String createTableName(Class tableClass) {
        return tableClass.getSimpleName();
    }

    /**
     * Gets string headers for table using tableClass class structure.
     *
     * @param tableClass target class (structure).
     * @return result List of String headers, if operation was successful, or null, if it wasn't.
     */
    public static List<String> createTableHeaders(Class tableClass) {
        if (tableClass == null) {
            return null;
        }

        return collectAllFields(tableClass).stream()
                .map(field -> Converter.convertToSQLType(field.getType()).equals("REF")
                        ? field.getName() + "_id"
                        : field.getName())
                .collect(Collectors.toList());
    }

    /**
     * Creates table head - String of coma-appended table headers.
     *
     * @param tableClass target class (structure).
     * @return result String of table head, if operation was successful, or null, if it wasn't.
     */
    public static String createTableHead(Class tableClass) {
        if (tableClass == null) {
            return null;
        }

        StringBuilder resultBuilder = new StringBuilder();
        createTableHeaders(tableClass).forEach(header -> {
            resultBuilder.append(header).append(", ");
        });

        if (resultBuilder.length() > 0) {
            resultBuilder.replace(resultBuilder.length() - 2, resultBuilder.length(), "");
        }

        return resultBuilder.toString();
    }

    /**
     * Creates String SQL record variant, valid for "VALUES" part of "INSERT" operation.
     *
     * @param tableClass target class (structure).
     * @param tableRecord object of type tableClass, which data will be used to create record.
     * @return result String SQL variant, if operation was successful, or null, if it wasn't.
     */
    public static String createTableRecord(Class tableClass, Object tableRecord) {
        if (tableClass == null || tableRecord == null) {
            return null;
        }

        StringBuilder resultBuilder = new StringBuilder();

        collectAllFields(tableClass).stream()
                .map(field -> createSQLFieldValue(tableClass, tableRecord, field))
                .forEach(value -> resultBuilder.append(value).append(", "));

        if (resultBuilder.length() > 0) {
            resultBuilder.replace(resultBuilder.length() - 2, resultBuilder.length(), "");
        }

        return resultBuilder.toString();
    }

    /**
     * Generates matcher for SQL operations (for example, "WHERE" operation),
     * which matches by id, or (if such field doesn't exist), by all other fields.
     *
     * @param tableClass target class (structure).
     * @param tableRecord target record object.
     * @return result String matcher, if operation was successful, or null, if it wasn't.
     */
    public static String createSmartIdMatcherOfRecord(Class tableClass, Object tableRecord) {
        if (tableClass == null || tableRecord == null) {
            return null;
        }

        String matcher = createIdMatcherOfRecord(tableClass, tableRecord);
        if (matcher == null) {
            matcher = createCompleteMatcherOfRecord(tableClass, tableRecord);
        }

        return matcher;
    }

    /**
     * Generates matcher for SQL operations (for example, "WHERE" operation),
     * which matches by all fields.
     *
     * @param tableClass target class (structure).
     * @param tableRecord target record object.
     * @return result String matcher, if operation was successful, or null, if it wasn't.
     */
    public static String createCompleteMatcherOfRecord(Class tableClass, Object tableRecord) {
        if (tableClass == null || tableRecord == null) {
            return null;
        }

        List<String> tableHeaders = createTableHeaders(tableClass);
        List<String> tableRecordValues = Arrays.asList(createTableRecord(tableClass, tableRecord).split(", "));
        if (tableHeaders.size() != tableRecordValues.size()) {
            return null;
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < tableHeaders.size(); i++) {
            resultBuilder.append(tableHeaders.get(i)).append("=").append(tableRecordValues).append(" AND ");
        }

        if (resultBuilder.length() > 0) {
            resultBuilder.replace(resultBuilder.length() - 5, resultBuilder.length(), "");
        }

        return resultBuilder.toString();
    }

    /**
     * Generates matcher for SQL operations (for example, "WHERE" operation),
     * which matches by id.
     *
     * @param tableClass target class (structure).
     * @param tableRecord target record object.
     * @return result String matcher, if operation was successful, or null, if it wasn't.
     */
    public static String createIdMatcherOfRecord(Class tableClass, Object tableRecord) {
        if (tableClass == null || tableRecord == null) {
            return null;
        }

        List<String> tableHeaders = createTableHeaders(tableClass);
        List<String> tableRecordValues = Arrays.asList(createTableRecord(tableClass, tableRecord).split(", "));

        int idIndex = tableHeaders.indexOf("id");
        if (idIndex != -1 && tableRecordValues.get(idIndex) != null) {
            return "id=" + tableRecordValues.get(idIndex);
        }

        return null;
    }

    /**
     * Creates new instance of tableClass object of String SQL record.
     *
     * @param tableClass target class (structure).
     * @param resultSet target SQL ResultSet with correctly positioned cursor (source of SQL record).
     * @return result new instance, if operation was successful, or null, if it wasn't.
     */
    public static <T> T recordToObject(Class<T> tableClass, ResultSet resultSet) {
        if (tableClass == null || resultSet == null) {
            return null;
        }

        final Map<String, String> resultSetMap;
        try {
            resultSetMap = Converter.resultSetToMap(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        final T newInstance;
        try {
            newInstance = tableClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        collectAllFields(tableClass).forEach(field -> {
            if (Converter.convertToSQLType(field.getType()).equals("REF")) {
                return;
            }

            String fieldValue = resultSetMap.get(field.getName());
            if (fieldValue != null) {
                try {

                    collectAllMethods(tableClass).stream()
                            .filter(method -> method.getName().equals(createSetterName(field)))
                            .findFirst().get()
                            .invoke(
                                    newInstance,
                                    Converter.stringToObject(field.getType(), fieldValue)
                            );

                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        return newInstance;
    }


    /**
     * Generates table fields.
     *
     * @param tableClass class, which fields are used to create SQL Table fields' structure.
     * @return result fields String.
     */
    private static String makeTableFields(Class tableClass) {
        StringBuilder result = new StringBuilder();

        Field[] fields = tableClass.getDeclaredFields();
        if (fields == null) {
            return "";
        }

        List<Field> fieldList = collectAllFields(tableClass);

        fieldList.stream()
                .map(ReflectionUtils::makeSQLField)
                .collect(Collectors.toList())
                .forEach(row -> result.append(row).append(",\n"));

        return result.length() > 0
                ? result.replace(result.length() - 2, result.length(), "").toString()
                : "";
    }

    /**
     * Collects all fields of class and its super classes (except Object class).
     *
     * @param type target class.
     * @return result List of fields, where the first positions holds the highest
     * "parents" of target class and the last positions - fields of target class.
     */
    private static List<Field> collectAllFields(Class type) {
        List<Field> fieldList = new LinkedList<>();

        Class superClass = type;
        while (!Object.class.equals(superClass)) {
            fieldList.addAll(0, Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        }

        return fieldList;
    }

    /**
     * Collects all methods of class and its super classes (except Object class).
     *
     * @param type target class.
     * @return result List of methods, where the first positions holds the highest
     * "parents" of target class and the last positions - methods of target class.
     */
    private static List<Method> collectAllMethods(Class type) {
        List<Method> methodList = new LinkedList<>();

        Class superClass = type;
        while (!Object.class.equals(superClass)) {
            methodList.addAll(0, Arrays.asList(superClass.getDeclaredMethods()));
            superClass = superClass.getSuperclass();
        }

        return methodList;
    }

    /**
     * Generates SQL Table field.
     *
     * @param field reflection Field, used to create SQL Table field structure.
     * @return result field String.
     */
    private static String makeSQLField(Field field) {
        String fieldType = Converter.convertToSQLType(field.getType());
        String fieldName = field.getName();

        if (fieldType.equals("REF")) {
            fieldType = "INT";
            fieldName += "_id";
        }

        return fieldName + " " + fieldType;
    }

    /**
     * Generates SQL Table constraints.
     *
     * @param tableClass class, which fields are used to create SQL Table constraints' structure.
     * @return result constraints String.
     */
    private static String makeTableConstraints(Class tableClass) {

        List<Field> fieldList = collectAllFields(tableClass);

        for (Field field : fieldList) {
            String primaryKey = makeSQLPrimaryKey(field);
            if (primaryKey != null) {
                return ",\n" + primaryKey + "\n";
            }
        }

        return "";
    }

    /**
     * Generates SQL Table primary key constraint.
     *
     * @param field reflection Field, used to create SQL Table primary key constraint.
     * @return result primary key constraint String.
     */
    private static String makeSQLPrimaryKey(Field field) {
        return field.getName().toLowerCase().equals("id") ? "PRIMARY KEY (id)" : null;
    }

    /**
     * Creates SQL record's field value (String variant).
     *
     * @param tableClass target class (structure) of table record.
     * @param tableRecord target object, which data is used to create table records's field value.
     * @param field target field of tableClass class.
     * @return result SQL field value, if operation was successful, or null, if it wasn't.
     */
    private static String createSQLFieldValue(Class tableClass, Object tableRecord, Field field) {

        if (Converter.convertToSQLType(field.getType()).equals("REF")) {
            return "NULL";  // TODO replace with recursive search
        }

        return String.class.equals(field.getType())
                ? "'" + getFieldValueString(tableClass, tableRecord, field) + "'"
                : getFieldValueString(tableClass, tableRecord, field);
    }

    /**
     * Gets String value of target field of tableClass from tableRecord object.
     * Uses getter invocation to get field value.
     *
     * @param tableClass target class (structure).
     * @param tableRecord target object of tableClass.
     * @param field target field.
     * @return result String value of field, if operation was successful, or null, if it wasn't.
     */
    private static String getFieldValueString(Class tableClass, Object tableRecord, Field field) {
        try {
            String getterName = createGetterName(field);

            Class superClass = tableClass;

            Method getter = null;

            do {
                try {
                    getter = superClass.getMethod(getterName);
                    break;
                } catch (NoSuchMethodException e) {
                    superClass = superClass.getSuperclass();
                }
            } while (!Object.class.equals(superClass));

            return getter == null ? null : getter.invoke(tableRecord).toString();

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Generates "getter" method name of field name.
     *
     * @param field target field.
     * @return result "getter" method name, if operation was successful, or null, if it wasn't.
     */
    private static String createGetterName(Field field) {
        return "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }

    /**
     * Generates "setter" method name of field name.
     *
     * @param field target field.
     * @return result "setter" method name, if operation was successful, or null, if it wasn't.
     */
    private static String createSetterName(Field field) {
        return "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
    }

}
