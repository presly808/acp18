package reflection.task1;

import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
        if (src == null || cls == null) {
            throw new NullPointerException("src or cls is null");
        }

        try {

            Object target = cls.newInstance();

            String[] rows = src.split("\n");
            rows = Arrays.copyOfRange(rows, 1, rows.length - 1);

            for (String row : rows) {
                String fieldName = row.split(":")[0].trim().replace("\"", "");
                String fieldValue = row.substring(row.indexOf(":") + 1).trim();
                fieldValue = brushFieldValue(fieldValue);

                Field targetField = cls.getField(fieldName);

                checkAnnotation(targetField);   // throws AnnotationFormatError !

                Object generatedFieldValue = makeFieldValueOfString(fieldValue, targetField.getType());

                targetField.set(target, generatedFieldValue);
            }

            return target;

        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException |
                AnnotationFormatError | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Beautifies field value String.
     *
     * @param fieldValue not formed String field value.
     * @return formed String field value.
     */
    private static String brushFieldValue(String fieldValue) {
        if (fieldValue.charAt(fieldValue.length() - 1) == ',') {
            fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
        }

        if (fieldValue.charAt(0) == '\"') {
            fieldValue = fieldValue.substring(1, fieldValue.length() - 1);
        }

        return fieldValue;
    }

    /**
     * Checks if annotated field value type is equal to actual.
     *
     * @param targetField Field, which will be checked.
     * @throws AnnotationFormatError if compared types (actual and annotated) are not equal.
     */
    private static void checkAnnotation(Field targetField) throws AnnotationFormatError {
        MyField attachedAnnotation = targetField.getDeclaredAnnotation(MyField.class);

        if (targetField.getType().isPrimitive() || !attachedAnnotation.type().equals(targetField.getType())) {
            throw new AnnotationFormatError(
                    String.format("Actual field type (%s) is primitive, or is not equal to annotated (%s).",
                            targetField.getType(), attachedAnnotation.type()));
        }
    }

    /**
     * Generates object of field type from String version of field value.
     *
     * @param fieldValue String field value, needed to be converted.
     * @param fieldType type of Field value.
     * @return constructed instance of field value type.
     * @throws NoSuchMethodException if field Class doesn't contain constructor with String param.
     * @throws IllegalAccessException if can't construct new instance of field type.
     * @throws InvocationTargetException if can't invoke constructor.
     * @throws InstantiationException if field type, is some reasons, can't be instantiated.
     */
    private static Object makeFieldValueOfString(String fieldValue, Class fieldType)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return fieldType.getConstructor(String.class).newInstance(fieldValue);
    }

}
