package db;

import db.Exception.*;
import db.Reflection.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static db.Reflection.ReflectionUtils.*;

public class SqlUtils {

    private String url;
    private static final Map<Type, String> SQL_TYPES_FOR_PRIMITIVES = new HashMap<>();

    static {
        SQL_TYPES_FOR_PRIMITIVES.put(Boolean.TYPE, "bit");
        SQL_TYPES_FOR_PRIMITIVES.put(Character.TYPE, "char(1)");
        SQL_TYPES_FOR_PRIMITIVES.put(Byte.TYPE, "tinyint");
        SQL_TYPES_FOR_PRIMITIVES.put(Short.TYPE, "smallint");
        SQL_TYPES_FOR_PRIMITIVES.put(Integer.TYPE, "int");
        SQL_TYPES_FOR_PRIMITIVES.put(Long.TYPE, "bigint");
        SQL_TYPES_FOR_PRIMITIVES.put(Float.TYPE, "float");
        SQL_TYPES_FOR_PRIMITIVES.put(Double.TYPE, "double");
    }

    SqlUtils(String url) {
        this.url = url;
    }

    private String getCreateTableStatement(Class clazz) throws NoPrimaryKeyException, NoTableClassException {
        StringBuilder sql = new StringBuilder();

        sql.append(getTableHeader(clazz));

        List<Field> fields = getAllFields(clazz);

        for (int i = 0; i < fields.size(); i++) {

            Field field = fields.get(i);

            sql.append(getFieldDeclarationStatement(field));

            if (i != fields.size() - 1) {
                sql.append(",");
                sql.append(System.getProperty("line.separator"));
            } else {
                sql.append(");");
            }
        }

        return sql.toString();
    }

    private String getFieldDeclarationStatement(Field field) throws NoPrimaryKeyException, NoTableClassException {

        StringBuilder sql = new StringBuilder();

        //add field name
        sql.append(String.format(" %s ", field.getName()));

        //add field type
        Type fieldType = field.getType();

        if (SQL_TYPES_FOR_PRIMITIVES.containsKey(fieldType)) {
            sql.append(SQL_TYPES_FOR_PRIMITIVES.get(fieldType));
        } else if (String.class == fieldType) {
            sql.append("varchar(30)");
        } else {
            Class fieldClass = (Class) fieldType;
            if (fieldClass.getAnnotation(TableClass.class) == null) {
                throw new NoTableClassException("Unknown reference class.");
            }

            sql.append("int REFERENCES ");

            Field primaryKeyField = getPrimaryKeyField(fieldClass);

            sql.append(String.format("%s(%s)",
                    fieldClass.getSimpleName(), primaryKeyField.getName()));

        }

        //add primary constraint
        if (field.getAnnotation(PrimaryKey.class) != null) {
            sql.append(" PRIMARY KEY NOT NULL");
        }

        return sql.toString();
    }

    private String getTableHeader(Class clazz) {
        return String.format("CREATE TABLE IF NOT EXISTS %s (%s",
                clazz.getSimpleName(), System.getProperty("line.separator"));
    }

    private int executeUpdate(String sqlStatement) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    private <T> List<T> executeQuery(Class<T> tClass, String sqlStatement) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlStatement);
            return getListFromResultSet(tClass, rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String executeQuery(String sqlStatement) {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlStatement);
            return convertResultSetToString(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private <T> List<T> getListFromResultSet(Class<T> clazz, ResultSet resultSet)
            throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        List<T> objectList = new ArrayList<>();

        while (resultSet.next()) {
            T instance = clazz.newInstance();
            List<Field> fields = ReflectionUtils.getAllFields(clazz);
            for (Field field : fields) {
                if (!hasColumn(resultSet, field.getName())) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                if (!isReferenceType(fieldType)) {
                    Object value = resultSet.getObject(field.getName());
                    setFieldValue(clazz, instance, field, value);
                } else {
                    Object primaryKeyValue = resultSet.getObject(field.getName());
                    Object referenceObj = selectByPrimaryKey(fieldType, primaryKeyValue);
                    setFieldValue(clazz, instance, field, referenceObj);
                }
            }
            objectList.add(instance);
        }
        return objectList;
    }

    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            if (columnName.equals(rsmd.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }

    private String convertResultSetToString(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        int columnCount;

        try {
            columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 0; i < columnCount; ) {
                    builder.append(resultSet.getString(i + 1));
                    if (++i < columnCount) builder.append(",");
                }
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T> String getInsertStatement(Class<T> clazz, T obj)
            throws IllegalAccessException, NoTableClassException, NoPrimaryKeyException, NoSuchMethodException, InvocationTargetException {

        StringBuilder sql = new StringBuilder(String.format("INSERT INTO %s ", clazz.getSimpleName()));

        List<Field> fields = getAllFields(clazz);
        sql.append(String.format("(%s)", getFieldNamesStatement(fields)));

        sql.append(getFieldValuesStatement(clazz, obj, fields));

        return sql.toString();
    }

    private <T> String getFieldValuesStatement(Class<T> clazz, T obj, List<Field> fields) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoTableClassException, NoPrimaryKeyException {

        StringBuilder sql = new StringBuilder("VALUES (");
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);

            Type fieldType = field.getType();

            if (SQL_TYPES_FOR_PRIMITIVES.containsKey(fieldType) || String.class == fieldType) {
                Object fieldValue = getFieldValue(clazz, obj, field);
                if (String.class == field.getType()) {
                    fieldValue = String.format("'%s'", fieldValue);
                }
                sql.append(fieldValue);
            } else {
                Class fieldClass = (Class) fieldType;
                if (fieldClass.getAnnotation(TableClass.class) == null) {
                    throw new NoTableClassException("Unknown reference class.");
                }

                Field primaryKeyField = getPrimaryKeyField(fieldClass);

                Object fieldValue = getFieldValue(clazz, obj, field);

                Object primaryKeyValue = (fieldValue == null) ? "NULL" :
                        getFieldValue(fieldClass, fieldValue, primaryKeyField);

                sql.append(primaryKeyValue);
            }

            if (i != fields.size() - 1) {
                sql.append(",");
            } else {
                sql.append(") ");
            }
        }

        return sql.toString();
    }

    private String getFieldNamesStatement(List<Field> fields) {
        StringBuilder sql = new StringBuilder();

        for (int i = 0; i < fields.size(); i++) {

            Field field = fields.get(i);

            sql.append(field.getName());

            if (i != fields.size() - 1) {
                sql.append(",");
            }
        }

        return sql.toString();
    }

    private String getFieldNamesStatement(List<Field> fields, Class clazz) {
        StringBuilder sql = new StringBuilder();

        for (int i = 0; i < fields.size(); i++) {

            Field field = fields.get(i);

            sql.append(String.format("%s.%s", clazz.getSimpleName(), field.getName()));

            if (i != fields.size() - 1) {
                sql.append(",");
            }
        }

        return sql.toString();
    }

    private <T> String getRemoveStatement(Class<T> clazz, String primaryKeyName, Object primaryKeyValue) {

        return String.format("DELETE FROM %s WHERE %s = %s",
                clazz.getSimpleName(),
                primaryKeyName,
                primaryKeyValue);
    }

    private <T> String getSelectByPrimaryKeyStatement(Class<T> clazz, String primaryKeyName, Object primaryKeyValue) {

        return String.format("SELECT * FROM %s WHERE %s = %s",
                clazz.getSimpleName(),
                primaryKeyName,
                primaryKeyValue);
    }

    public <T> T selectByPrimaryKey(Class<T> clazz, Object primaryKeyValue) {

        String selectStatement;
        try {
            Field primaryKeyField = getPrimaryKeyField(clazz);
            selectStatement = getSelectByPrimaryKeyStatement(clazz, primaryKeyField.getName(), primaryKeyValue);
            List<T> objectList = executeQuery(clazz, selectStatement);
            return (objectList == null || objectList.size() != 1) ? null : objectList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> boolean removeObject(Class<T> clazz, Object primaryKeyValue) {
        String removeStatement;
        try {
            Field primaryKeyField = getPrimaryKeyField(clazz);
            removeStatement = getRemoveStatement(clazz, primaryKeyField.getName(), primaryKeyValue);
            return executeUpdate(removeStatement) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public <T> boolean addObject(Class<T> clazz, T obj) {
        String insertStatement;
        try {
            insertStatement = getInsertStatement(clazz, obj);
            return executeUpdate(insertStatement) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createTable(Class clazz) {
        String createTableStatement;
        try {
            createTableStatement = getCreateTableStatement(clazz);
            return executeUpdate(createTableStatement) == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean dropTable(Class clazz) {
        String dropTableStatement = "DROP TABLE " + clazz.getSimpleName();
        return executeUpdate(dropTableStatement) == 0;
    }

    public <T> List<T> getAllValues(Class<T> clazz) {
        String selectStatement;
        try {
            selectStatement = getSelectAllValuesStatement(clazz);
            return executeQuery(clazz, selectStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T> String getSelectAllValuesStatement(Class<T> clazz) {
        return String.format("SELECT * FROM %s",
                clazz.getSimpleName());
    }

    public int removeAllValues(Class clazz) {
        String removeStatement;
        try {
            removeStatement = getRemoveAllValuesStatement(clazz);
            return executeUpdate(removeStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    private String getRemoveAllValuesStatement(Class clazz) {
        return String.format("DELETE FROM %s",
                clazz.getSimpleName());
    }

    public <T> List<T> selectWithFilter(Class<T> clazz, Map<Field, Object> filters, Field orderBy, int limit) {
        String selectWithFilterStatement;
        try {
            selectWithFilterStatement = getSelectWithFilterStatement(clazz, filters, orderBy, limit);
            return executeQuery(clazz, selectWithFilterStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T> String getSelectWithFilterStatement(Class<T> clazz, Map<Field, Object> filters, Field orderBy, int limit)
            throws NoPrimaryKeyException, NoTableClassException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StringBuilder sql = new StringBuilder();

        List<Field> fields = getAllFields(clazz);

        //add first line
        sql.append(String.format("SELECT %s FROM %s %s",
                getFieldNamesStatement(fields, clazz),
                clazz.getSimpleName(),
                System.getProperty("line.separator")));

        if (filters != null) {
            //add joins
            List<Field> filterFields = new ArrayList<>(filters.keySet());
            for (Field field : filterFields) {

                Class<?> fieldType = field.getType();
                if (isReferenceType(fieldType) && clazz != fieldType) {
                    sql.append(getJoinStatement(clazz, field));
                }
            }

            //add WHERE conditions
            sql.append(String.format("WHERE %s", System.getProperty("line.separator")));

            for (int i = 0; i < filterFields.size(); i++) {
                if (i != 0) {
                    sql.append("AND ");
                }
                sql.append(getWhereStatement(filterFields.get(i), filters.get(filterFields.get(i))));
            }
        }

        //add order
        sql.append(String.format("ORDER BY %s.%s %s",
                clazz.getSimpleName(),
                orderBy.getName(),
                System.getProperty("line.separator")));

        //add limit
        sql.append(String.format("LIMIT %d", limit));

        return sql.toString();
    }

    private String getWhereStatement(Field field, Object fieldValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoPrimaryKeyException {

        Class fieldType = field.getType();

        if (!isReferenceType(fieldType)) {
            if (String.class == fieldType) {
                fieldValue = String.format("'%s'", fieldValue);
            }
        } else {
            Field primaryKeyField = getPrimaryKeyField(fieldType);
            fieldValue = getFieldValue(fieldType, fieldValue, primaryKeyField);
        }

        return String.format("%s = %s %s",
                field.getName(),
                fieldValue,
                System.getProperty("line.separator"));
    }

    private <T> String getJoinStatement(Class<T> clazz, Field field) throws NoPrimaryKeyException, NoTableClassException {

        Class fieldType = field.getType();

        if (fieldType.getAnnotation(TableClass.class) == null) {
            throw new NoTableClassException("Unknown reference class.");
        }

        Field primaryKeyField = getPrimaryKeyField(fieldType);

        return String.format("JOIN %s ON %s.%s = %s.%s %s",
                fieldType.getSimpleName(),
                clazz.getSimpleName(),
                field.getName(),
                fieldType.getSimpleName(),
                primaryKeyField.getName(),
                System.getProperty("line.separator"));
    }

    public <T, V, K> Map<K, V> getAgrFuncOnFieldGroupByField(
            Class<T> clazz, String agrFunc, Field agrField, Field groupField) {
        String selectAgrFuncOnFieldGroupByFieldStatement;
        try {
            selectAgrFuncOnFieldGroupByFieldStatement =
                    getAgrFuncOnFieldGroupByFieldStatement(clazz, agrFunc, agrField, groupField);

            List<T> listEnteties = executeQuery(clazz, selectAgrFuncOnFieldGroupByFieldStatement);

            Function<T, K> groupFieldFunc = convertGetterToFunc(clazz, groupField);
            Function<T, V> agrFieldFunc = convertGetterToFunc(clazz, agrField);

            return listEnteties.stream().collect(Collectors.toMap(groupFieldFunc, agrFieldFunc));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private <T> String getAgrFuncOnFieldGroupByFieldStatement(
            Class<T> clazz, String agrFunc, Field agrField, Field groupField) {
        return String.format("SELECT %s, %s(%s) AS %s FROM %s GROUP BY %s",
                groupField.getName(),
                agrFunc,
                agrField.getName(),
                agrField.getName(),
                clazz.getSimpleName(),
                groupField.getName());
    }
}
