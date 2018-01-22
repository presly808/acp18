package reflection.task1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target){
        Object obj = null;
        try {
             obj = target.getClass().getMethod("toString").invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        return (String) obj;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target){

        List<Field> fields = Arrays.asList(target.getClass().getFields());
//        fields.stream().filter(field -> f)



        return null;
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){
        return null;
    }

}
