package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class targetClass = target.getClass();
        Method toStringMethod = targetClass.getMethod("toString");
        return (String) toStringMethod.invoke(target);
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target){

        Class targetClass = target.getClass();
        Field[] fields = targetClass.getFields();
        StringBuilder sb = new StringBuilder();

        for (Field field: fields) {

            try {
                sb.append(String.format("%s:%s\n", field.getName(), field.get(target)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){

        String[] lines = src.split("\n");
        String[] fieldsLines = Arrays.copyOfRange(lines,1,lines.length-1);

        Map<String,String> keyValuesMap = new HashMap<>();

        for (int i = 0; i < fieldsLines.length; i++) {
            String[] keyValue = fieldsLines[i].split(":");
            String key = keyValue[0].substring(3,keyValue[0].length()-1);
            if (keyValue[1].charAt(0)==(char)34){
                keyValue[1] = keyValue[1].substring(1,keyValue[1].length()-1);
            }

            if (i != fieldsLines.length-1){
                keyValue[1] = keyValue[1].substring(0,keyValue[1].length()-1);
            }

            keyValuesMap.put(key, keyValue[1]);
        }

        try {
            Object instance = cls.newInstance();

            for(Field field : cls.getDeclaredFields()){
                MyField myField = field.getAnnotation( MyField.class );
                if ((myField != null) && (keyValuesMap.containsKey(field.getName()))){
                    Type fieldType = field.getType();

                    if(int.class == fieldType){
                        field.set(instance, Integer.parseInt(keyValuesMap.get(field.getName())));
                    } else if(double.class == fieldType){
                        field.set(instance, Double.parseDouble(keyValuesMap.get(field.getName())));
                    } else if(String.class == fieldType){
                        field.set(instance, keyValuesMap.get(field.getName()));
                    }
                }
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
