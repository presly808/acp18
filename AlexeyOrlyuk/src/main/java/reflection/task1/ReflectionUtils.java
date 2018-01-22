package reflection.task1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ArtCode course tasks.
 *
 * Task name: Reflection.
 * Started on: Week2_Day2.
 *
 * @author alex323glo
 * @version 1.0
 */
public class ReflectionUtils {
    /**
     * Invokes toString() method of target object using Reflection API.
     *
     * @param target object, whose toString() method will be invoked.
     * @return result of toString() method execution.
     */
    public static String invokeToString(Object target){
        if (target == null) {
            throw new NullPointerException("target is null");
        }

        Class targetStructure = target.getClass();

        try {
            Method toStringMethod = targetStructure.getMethod("toString");
            return (String) toStringMethod.invoke(target);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converts all public fields into json string.
     *
     * @param target converted object.
     * @return result JSON object as String.
     */
    public static String convertToJson(Object target){
        if (target == null) {
            return null;
        }

        Class targetStructure = target.getClass();
        Field[] targetFields = targetStructure.getDeclaredFields();
        StringBuilder resultBuilder = new StringBuilder("{\n");

        try {
            for (Field field : targetFields) {
                if (!field.isAnnotationPresent(MyField.class)) {
                    continue;
                }

                String fieldName = field.getName();
                Object fieldValue = field.get(target);
                resultBuilder.append("\t").append(fieldName).append(":").append(fieldValue).append(",\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        return resultBuilder.append("}").toString();
    }

    /**
     * Converts all fields that were annotated by SpecificAnnotation into json string.
     *
     * @param src source JSON String, which will be converted.
     * @param cls type of result object.
     * @return result object of type "cls".
     */
    public static Object converFromJson(String src, Class cls){

        return null;
    }

}
