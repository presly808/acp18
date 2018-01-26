package reflection.task1;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReflectionUtils {


    // using reflection
    public static String invokeToString(Object target){
        Class targetClass = target.getClass();
        String res = "";
        try {
            Method targetMethodToString = targetClass.getMethod("toString");
            res = (String) targetMethodToString.invoke(target);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return res;
    }



    /*convert all public fields into json string*/
    public static String convertToJson(Object target){
    StringBuilder res = new StringBuilder();
    res.append("{\n");
    Class classTarget = target.getClass();
    Field[] fields = classTarget.getFields();
    for (Field field : fields){
        try {
            res.append("\"").append(field.getName()).append("\"").append(":");
            String typeOfValue = field.get(target).getClass().toString();
            if (typeOfValue.contains("String"))
                res.append("\"").append(field.get(target)).append("\"");
            else
                res.append(field.get(target));
            res.append("\n");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    return res.append("}").toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){
        src = src.substring(3, src.length() - 2);
        String[] lines = src.split("\n");
        Map<String, String> mapFieldValue = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(.+?)\"");
        for (String line : lines){
            String[] strings = line.split(":");
            Matcher matcher0 = pattern.matcher(strings[0]);
            Matcher matcher1 = pattern.matcher(strings[1]);
            matcher0.find();
            if(matcher1.find())
                mapFieldValue.put(matcher0.group(1), matcher1.group(1));
            else
                mapFieldValue.put(matcher0.group(1), strings[1]);
        }


        try {
            Object res = cls.newInstance();
            Map <String, Field> onlyAnnotationFileds = new HashMap<>();
            for (Field field : cls.getDeclaredFields()) {
                MyField myField = field.getAnnotation(MyField.class);
                if (myField != null)
                    onlyAnnotationFileds.put(field.getName(), field);
            }

            for (String fieldName : onlyAnnotationFileds.keySet()){
                Field field = onlyAnnotationFileds.get(fieldName);
                Class<?> fieldType = field.getType();
                String nameCamelCase = "set" + field.getName().substring(0,1).toUpperCase() + fieldName.substring(1);
                Method setMethod = cls.getMethod(nameCamelCase, fieldType);
                if (int.class == fieldType)
                setMethod.invoke(res, Integer.parseInt(mapFieldValue.get(fieldName)));
                if (double.class == fieldType)
                setMethod.invoke(res, Double.parseDouble(mapFieldValue.get(fieldName)));
                if (String.class == fieldType)
                setMethod.invoke(res, mapFieldValue.get(fieldName));
            }
            return res;


        } catch (InstantiationException | IllegalAccessException |
                NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

}
