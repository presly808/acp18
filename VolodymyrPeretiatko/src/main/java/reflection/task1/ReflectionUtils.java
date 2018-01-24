package reflection.task1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (String) invoke(target, "toString");
    }

    public static Method getMethodByName(Object target, String name) throws NoSuchMethodException {
        return target.getClass().getMethod(name);
    }

    public static Object invoke(Object target, String methName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return getMethodByName(target, methName).invoke(target);
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target){

        List<Field> fields = getAnnotatedFields(target.getClass(), MyField.class);

        for (Field f : fields){
            System.out.println(f.getName());
        }

        return null;
    }

    public static List<Field> getAnnotatedFields(Class target, Class anotation){

        return (ArrayList<Field>) Arrays.stream(target.getDeclaredFields())
                                        .filter(f -> f.isAnnotationPresent(anotation));
                                        //.collect(Collectors.toList());
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){
        return null;
    }

}
