package reflection.task1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target){
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
    public static String convertToJson(Object target){
        StringBuilder sb = new StringBuilder();
        Class struc = target.getClass();
        Field[] fields = struc.getFields();
        for(Field field : fields){
            try {
                String firstUpperCaseLetter = field.getName().substring(0, 1).toUpperCase();
                String fieldGetName = firstUpperCaseLetter + field.getName().substring(1);

                Method fieldValue = struc.getMethod("get" + fieldGetName);
                sb.append(String.format("%s: ", field.getName() + fieldValue.invoke(target)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        /*if (field.isAnnotationPresent(Save.class)) {
            Save save = field.getDeclaredAnnotation(Save.class);
            String fieldName = field.getName();
            String firstUpperCaseLetter = fieldName.substring(0, 1).toUpperCase();
            Method getFieldValue = struc.getMethod("get" + firstUpperCaseLetter + fieldName.substring(1));
            sb.append(String.format("%s:%s\n", save.name(), getFieldValue.invoke(obj)));
        }*/

        return sb.toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){
        return null;
    }

}
/*
public class ReflectionFormatter {

    public String format(Object obj) {
        Class struc = obj.getClass();
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s:%s\n", "type", struc.getName()));

        Field[] fields = struc.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.isAnnotationPresent(Save.class)) {
                    Save save = field.getDeclaredAnnotation(Save.class);
                    String fieldName = field.getName();
                    String firstUpperCaseLetter = fieldName.substring(0, 1).toUpperCase();
                    Method getFieldValue = struc.getMethod("get" + firstUpperCaseLetter + fieldName.substring(1));
                    sb.append(String.format("%s:%s\n", save.name(), getFieldValue.invoke(obj)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public Object parse(String src) {

        String[] lines = src.split("\n");
        String type = lines[0].split(":")[1];

        Map<String, String> keyValuesMap = new HashMap<>();

        for (int i = 1; i < lines.length; i++) {
            String[] keyValue = lines[i].split(":");
            keyValuesMap.put(keyValue[0], keyValue[1]);
        }

        try {
            Class cl = Class.forName(type);
            Object instance = cl.newInstance();


            Map<String, Field> fieldAnnotNameMap = new HashMap<>();
            for (Field field : cl.getDeclaredFields()) {
                Save save = field.getAnnotation(Save.class);
                if (save != null) {
                    fieldAnnotNameMap.put(save.name(), field);
                }
            }

            // iteration over map
            for (String key : keyValuesMap.keySet()) {
                // getField using Annotation property
                Field field = fieldAnnotNameMap.get(key);

                Class<?> fieldType = field.getType();

                String firstUpperCaseLetter = field.getName().substring(0, 1).toUpperCase();
                String other = field.getName().substring(1);
                Method setMethod = cl.getMethod("set" + firstUpperCaseLetter + other, fieldType);

                if (int.class == fieldType) {
                    setMethod.invoke(instance, Integer.parseInt(keyValuesMap.get(key)));
                } else if (double.class == fieldType) {
                    setMethod.invoke(instance, Double.parseDouble(keyValuesMap.get(key)));
                } else if (String.class == fieldType) {
                    setMethod.invoke(instance, keyValuesMap.get(key));
                }

            }

            return instance;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }


}
*/
