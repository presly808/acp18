package reflection.task1;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) {

        Class structure = target.getClass();

        return invokeToString(target, structure);
    }

    private static String invokeToString(Object target, Class structure) {
        try {
            Method myToString = structure.getMethod("toString");
            return (String) myToString.invoke(target);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) {

        Class structure = target.getClass();

        StringBuilder sb = new StringBuilder();
        Field[] fields = structure.getFields();

        return convertFieldsToJson(target, sb, fields);
    }

    private static String convertFieldsToJson(Object target, StringBuilder sb, Field[] fields) {
        for (Field field : fields) {
            try {
                sb.append(String.format("%s:%s\n", field.getName(), field.get(target)));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) throws IllegalAccessException, InstantiationException {


        String[] strings = src.substring(1, src.length() - 1)
                .replaceAll("\"", "")
                .split(",");

        Object object = cls.newInstance();

        convertFieldWithAnnotationFromJson(object, getAnnotatedFields(cls, MyField.class), getJsonHashMap(strings));

        return object;
    }

    private static List<Field> getAnnotatedFields(Class target, Class annotation) {

        return (ArrayList<Field>) Arrays.stream(target.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private static void convertFieldWithAnnotationFromJson(Object object, List<Field> fields, Map<String, String> jsonHashMap) {

        for (Field field : fields) {
            try {
                if (field.getType().equals(int.class)) {
                    field.setInt(object, Integer.parseInt(jsonHashMap.get(field.getName())));
                } else {
                    field.set(object, jsonHashMap.get(field.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    private static Map<String, String> getJsonHashMap(String[] strings) {
        Map<String, String> jsonMap = new HashMap<>();

        for (String pair : strings) {
            String[] entry = pair.split(":");
            jsonMap.put(entry[0].trim(), entry[1].trim());
        }
        return jsonMap;
    }

}
