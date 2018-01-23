package reflection.task1;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target){
        String res = "";
        try {
            Method method = target.getClass().getMethod("toString");
            res = method.invoke(target).toString();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
        return res;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target){
        StringBuilder sb = new StringBuilder();
        sb.append("}\n");
        Class cltarget = target.getClass();
        Field[] fields = cltarget.getFields();
        for (Field field : fields) {
            try {
                Object val = field.get(target);
                sb.append(field).append(" : ").append(val).append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  sb.append("}"). toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){
        Map<String, String> mapKV = new HashMap<>();
        String[] strKV = parseJsonToStrFormat(src).split(",");
        for (String s : strKV) {
            String[] kv = s.split(":");
            mapKV.put(kv[0], kv[1]);
        }
        try {
            Object instance = cls.newInstance();

            Map<String, Field> annotationMap = new HashMap<>();
            for (Field field : cls.getDeclaredFields()) {
                MyField myField = field.getAnnotation(MyField.class);
                if (myField != null){
                    annotationMap.put(field.getName(), field);
                }
            }
            for (String key : mapKV.keySet()) {
                Field field = annotationMap.get(key);
                Class<?> fieldType = field.getType();
                String firstLetterToUppCase = field.getName().substring(0,1).toUpperCase();
                String other = field.getName().substring(1);
                Method setMethod = cls.getMethod("set" + firstLetterToUppCase + other, fieldType);

                if(int.class == fieldType){
                    setMethod.invoke(instance, Integer.parseInt(mapKV.get(key)));
                } else if(double.class == fieldType){
                    setMethod.invoke(instance, Double.parseDouble(mapKV.get(key)));
                } else if(String.class == fieldType){
                    setMethod.invoke(instance, mapKV.get(key));
                }
            }
            return instance;
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

    private static String parseJsonToStrFormat(String json){
        return json.substring(1, json.length()-1)
                .replaceAll("\n", "")
                .replaceAll("\"","");
    }

}
