package reflection.task1;

import com.google.gson.Gson;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) {
        Class targetClass = target.getClass();
        String s = "";
        try {
            Method toStringMethod = targetClass.getMethod("toString");
            s = (String) toStringMethod.invoke(target);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return s;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        Class targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();

        Gson gson = new Gson();
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers())) {
                sb.append(field.get(target));
            }
        }

        return gson.toJson(sb.toString());
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object convertFromJson(String src, Class cls) {

        String[] linesOfJson = src
                .replaceAll(",", "")
                .replaceAll("\"", "")
                .split("\n");

        Map<String, String> keyValuesMap = new HashMap<>();

        for (int i = 1; i < linesOfJson.length - 1; i++) {
            String[] keyAndValue = linesOfJson[i].trim().split(":");
            keyValuesMap.put(keyAndValue[0], keyAndValue[1]);
        }

        Object obj = null;
        try {
            obj = cls.newInstance();

            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(MyField.class)) {

                    Type fieldType = field.getType();

                    if (fieldType == double.class) {
                        field.set(obj, Double.parseDouble(keyValuesMap.get((field.getName()))));
                    } else if (fieldType == int.class) {
                        field.set(obj, Integer.parseInt(keyValuesMap.get((field.getName()))));
                    } else if (fieldType == boolean.class) {
                        field.set(obj, Boolean.valueOf(keyValuesMap.get((field.getName()))));
                    } else {
                        field.set(obj, keyValuesMap.get((field.getName())));
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