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
    public static String invokeToString(Object target) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (String) target.getClass().getMethod("toString").invoke(target);
    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target) {

        StringBuilder sb = new StringBuilder();
        List<Field> fields = Arrays.asList(target.getClass().getFields());
        fields.forEach(field -> {
            try {
                if(field.getType().isPrimitive()) {
                    sb.append("\"").append(field.getName()).append("\"").append(":").
                            append(field.get(target)).append(",\n");
                }
                 else {
                    sb.append("\"").append(field.getName()).append("\"").append(":").append("\"").
                            append(field.get(target)).append("\",\n");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        sb.insert(0, "{\n").delete(sb.length()-2, sb.length()-1).append("}");

        return sb.toString();
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls) throws IllegalAccessException, InstantiationException {
        Object obj = cls.newInstance();

        src = src.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("}", "");

        List<Field> fieldsClass = Arrays.asList(cls.getFields());
        String finalSrc = src;
        fieldsClass.forEach((Field field) -> {

            String value = finalSrc.split(field.getName()+":")[1];
            if (value.contains(",")) {
                value = value.substring(0,value.indexOf(","));
            } else {
                value = value.substring(0,value.length()-1);
            }
            try {
                if (field.getType().equals(int.class)) {

                    field.set(obj, Integer.parseInt(value));
                } else {
                    field.set(obj, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });

        return obj;
    }
}
