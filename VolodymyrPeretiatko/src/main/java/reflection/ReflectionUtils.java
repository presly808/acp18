package reflection;


import java.io.IOException;
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
    public static String invokeToString(Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (String) invoke(target, "toString");
    }

    public static Object invoke(Object target, String methName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return getMethodByName(target, methName).invoke(target);
    }

    public static Method getMethodByName(Object target, String name) throws NoSuchMethodException {
        return target.getClass().getMethod(name);
    }


    /*convert all public fields into json string*/
    public static String convertToJson(Object target) throws IllegalAccessException {

        List<Field> fields = getAnnotatedFields(target.getClass(), MyField.class);

        String result = "{\n";
        result += fields.stream()
                        .peek(f -> f.setAccessible(true))
                        .map(f -> {
                            try {
                                return f.getName() + ":" + f.get(target);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                return "";
                            }
                        })
                        .collect(Collectors.joining(",\n"));

        return result + "}";
    }

    public static List<Field> getAnnotatedFields(Class target, Class anotation){

        return (ArrayList<Field>) Arrays.stream(target.getDeclaredFields())
                                        .filter(f -> f.isAnnotationPresent(anotation))
                                        .collect(Collectors.toList());
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) throws IOException, IllegalAccessException, InstantiationException {

        Object obj = cls.newInstance();

        setObjFildsByMap(obj, getAnnotatedFields(cls, MyField.class),
                              getMapFromString(src, ",", ":"));

        return obj;
    }

    public static void setObjFildsByMap(Object target, List<Field> filds, Map<String, String> values){

        for(Field f : filds){
            try {
                if (f.getType().equals(int.class)) {
                    f.setInt(target, Integer.parseInt(values.get(f.getName())));
                } else {
                    f.set(target, values.get(f.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, String> getMapFromString(String src, String pairSeparator, String keyValueSeparator){

        Map<String,String> valuesMap = new HashMap<>();

        String[] keyValuePairs = src.substring(1, src.length()-1)                     //remove curly brackets
                                    .replaceAll("\"", "")
                                    .split(pairSeparator);

        for(String pair : keyValuePairs) {
            String[] entry = pair.split(keyValueSeparator);
            valuesMap.put(entry[0].trim(), entry[1].trim());
        }

        return valuesMap;
    }



}
