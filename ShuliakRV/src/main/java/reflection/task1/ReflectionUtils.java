package reflection.task1;

import java.lang.reflect.Field;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target) {

        String res = "";

        try {
            res = (String) target.getClass().getMethod("toString").invoke(target);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return res;
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) {

        Field[] fields = target.getClass().getFields();

        StringBuilder res = new StringBuilder();

        res.append("{\n");

        StringBuilder value = new StringBuilder();

        StringBuilder ch = new StringBuilder("\"");

        for (Field f : fields) {

            try {
                value.append(f.get(target).toString());
            } catch (Exception e) {
                value.append("null");
            }

         //   if (f.getType()!=String.class) ch.deleteCharAt(0);

            res.append("\"" + f.getName() + "\"" + ":" + ch + value + ch + ",\n");

            value.delete(0,value.length());
        }
        return res.deleteCharAt(res.length() - 2).append("}").toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) {
        return null;
    }

}
