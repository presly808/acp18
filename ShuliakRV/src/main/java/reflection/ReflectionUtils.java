package reflection;

import java.lang.reflect.Field;
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
            res = (String) target.getClass().
                    getMethod("toString").invoke(target);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return res;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) {

        StringBuilder res = new StringBuilder();

        res.append("{\n");

        StringBuilder value = new StringBuilder();

        String ch = "";

        for (Field field : target.getClass().getFields()) {

            try {
                value.append(field.get(target).toString());
            } catch (Exception e) {
                value.append("null");
            }

            if (field.getType() != String.class) {
                ch = "";
            } else {
                ch = "\"";
            }

            res.append("\"" + field.getName() + "\"" + ":"
                    + ch + value + ch + ",\n");

            value.delete(0, value.length());
        }
        return res.deleteCharAt(res.length() - 2).append("}").toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) {

        String[] lines = src.split("\n");

        Map<String, String> keyValueMap = new HashMap<>();

        for (int i = 1; i < lines.length - 1; i++) {
            String[] keyValue = lines[i].split(":");
            keyValueMap.put(keyValue[0].trim(), keyValue[1].trim().
                    replace(",", ""));
        }

        try {

            Object obj = cls.newInstance();

            for (Field field : cls.getDeclaredFields()) {

                if (field.isAnnotationPresent(MyField.class)) {
                    String value = keyValueMap.
                            get("\"" + field.getName() + "\"");
                    if (value != null) {

                        if (field.getType() == int.class) {
                            field.set(obj, Integer.parseInt(value));
                        } else {
                            if (field.getType() == double.class) {
                                field.set(obj, Double.parseDouble(value));
                            } else {
                                if (field.getType() == String.class) {
                                    field.set(obj, value.replace("\"", ""));
                                }
                            }
                        }
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
