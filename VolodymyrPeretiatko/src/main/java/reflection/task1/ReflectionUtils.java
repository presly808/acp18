package reflection.task1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) {

        Method method;
        String result = "";

        try {
            method = target.getClass().getMethod("toString");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return "";
        }

        try {
            result = (String) method.invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target){
        return null;
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){
        return null;
    }

}
