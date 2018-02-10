package db.Reflection;

import db.Exception.NoPrimaryKeyException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ReflectionUtils {

    public static List<Field> getAllFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        Class current = clazz;

        while (current != null) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

    private static Method getMethod(Class clazz, String methodName) throws NoSuchMethodException {
        Class current = clazz;

        while (current != null) {
            try {
                return current.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method not found.");
    }

    private static Method getMethod(Class clazz, String methodName, Class paramClass) throws NoSuchMethodException {
        Class current = clazz;

        while (current != null) {
            try {
                return current.getDeclaredMethod(methodName, paramClass);
            } catch (NoSuchMethodException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method not found.");
    }

    public static Field getPrimaryKeyField(Class fieldClass) throws NoPrimaryKeyException {
        Field primaryKeyField = null;
        for (Field f : getAllFields(fieldClass)) {
            if (f.isAnnotationPresent(PrimaryKey.class)) {
                primaryKeyField = f;
                break;
            }
        }
        if (primaryKeyField == null) {
            throw new NoPrimaryKeyException("Primary key not found.");
        }
        return primaryKeyField;
    }

    public static <T> Object getFieldValue(Class<T> clazz, T obj, Field field)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getter = getGetter(clazz, field.getName());
        return getter.invoke(obj);
    }

    public static <T> void setFieldValue(Class<T> clazz, T obj, Field field, Object value)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method setter = getSetter(clazz, field);
        setter.invoke(obj,value);
    }

    private static <T> Method getGetter(Class<T> clazz, String fieldName) throws NoSuchMethodException {
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return getMethod(clazz, getterName);
    }

    private static <T> Method getSetter(Class<T> clazz, Field field) throws NoSuchMethodException {
        String fieldName = field.getName();
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return getMethod(clazz, setterName, field.getType());
    }

    public static boolean isReferenceType(Class fieldType) {
        return !(fieldType.isPrimitive() || String.class == fieldType);
    }

    public static <E,T> Function<E,T> convertGetterToFunc (Class<E> clazz, Field field) throws NoSuchMethodException {
        Method getter = getGetter(clazz, field.getName());
        return  (E e) -> {
            try {
                return (T)getter.invoke(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        };
    }
}
