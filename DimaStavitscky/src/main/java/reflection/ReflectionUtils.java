package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) {
        String res = "";
        try {
            Method method = target.getClass().getMethod("toString");
            res = method.invoke(target).toString();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return res;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) {
        StringBuilder sb = new StringBuilder();
        Class struc = target.getClass();
        Field[] fields = struc.getFields();

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
    public static Object converFromJson(String src, Class cls) {

        String[] lines = src
                .replaceAll(",", "")
                .replaceAll("\"", "")
                .split("\n");

        Map<String, String> keyValues = new HashMap<>();

        for (int i = 1; i < lines.length - 1; i++) {
            String[] keyAndValue = lines[i].trim().split(":");
            keyValues.put(keyAndValue[0], keyAndValue[1]);
        }

        try {
            Object obj = cls.newInstance();

            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(MyField.class)) {
                    Type type = field.getType();
                    if (type == double.class) {
                        field.set(obj, Double.parseDouble(keyValues.get((field.getName()))));
                    } else if (type == int.class) {
                        field.set(obj, Integer.parseInt(keyValues.get((field.getName()))));
                    } else if (type == boolean.class) {
                        field.set(obj, Boolean.valueOf(keyValues.get((field.getName()))));
                    } else {
                        field.set(obj, keyValues.get((field.getName())));
                    }
                }
            }

            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}