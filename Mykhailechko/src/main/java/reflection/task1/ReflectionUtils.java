package reflection.task1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> cls = target.getClass();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getName());

            if(field.isAnnotationPresent(MyField.class)) {
                Method method = cls.getDeclaredMethod("toString");
                return (String) method.invoke(target);
            }
        }
       return null;
    }


    /*convert all public fields into json string*/
    public static String convertToJson(Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class<?> cls = target.getClass();
        Field[] fields = cls.getDeclaredFields();

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for (Field field : fields) {
            sb.append("\"" +field.getName() + "\":\"" + field.get(target) + "\"\n");
        }

        return sb.append("}").toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        Object obj = cls.newInstance();

        String[] str = src.substring(1,src.length()-1)
                          .replaceAll("\"","")
                          .split(",");

        Field[] fields = cls.getFields();

        for (Field field : fields) {

            if(field.isAnnotationPresent(MyField.class)) {

                for (String s  : str) {
                    System.out.println(s);
                    if (s.contains(field.getName())){

                        if(s.substring(s.indexOf(":")+1).matches("[0-9]+")) {
                            field.set(obj, Integer.parseInt(s.substring(s.indexOf(":") + 1)));
                        }
                        else{
                            field.set(obj,s.substring(s.indexOf(":")+1));
                        }
                    }
                }
            }
        }
        return obj;
    }
}