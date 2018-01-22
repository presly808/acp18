package reflection.task1;

import java.lang.reflect.Field;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) {

        String str = "";

        try {
            str =(String) target.getClass().getMethod("toString").invoke(target);
        } catch(Exception e) {
            System.out.println(e.toString());
        }
        return str;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) {

        Field[] fields = target.getClass().getFields();
        String res = "";
        for (Field f : fields){
            f.getName()||":"||f.get
        }
        return res;
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) {
        return null;
    }

}
