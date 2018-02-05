package db.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

}
