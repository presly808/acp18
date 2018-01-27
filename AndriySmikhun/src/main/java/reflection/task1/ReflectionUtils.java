package reflection.task1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.SocketHandler;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtils {

    // using reflection
    public static String invokeToString(Object target){

        Class tmpclass = target.getClass();

        String result = "<" + tmpclass.getSimpleName() + ">";

        Field[] filds = tmpclass.getFields();

        for (Field fild:filds) {
            result += "<" + fild.getName() + ">";
            try {
                result += fild.get(target) + "</" + fild.getName() + ">" ;
            } catch (IllegalAccessException e) {
            }

        }
        System.out.println(result);



        return result;

    }

    /*convert all public fields into json string*/
    public static String convertToJson(Object target){

        Class tmpclass = target.getClass();

        String result = "\"" + tmpclass.getSimpleName() + "\":{";

        Field[] filds = tmpclass.getFields();

        for (Field fild:filds) {
            result += "\"" + fild.getName() + "\":";
            try {
                result += fild.get(target) + "\"" + fild.getName() + "\"" ;
            } catch (IllegalAccessException e) {
            }

        }
        result +="}";
        System.out.println(result);



        return result;
    }

    /*convert all fields that were annotated by SpecificAnnotation into json string*/
    public static Object converFromJson(String src, Class cls){

        try {
            Object tmpo = cls.newInstance();
        }catch (IllegalAccessException e){
        }catch (InstantiationException e){
        }
        Field[] filds = cls.getFields();
        for (Field fild: filds) {
            System.out.println(fild.isAnnotationPresent(MyField.class));

        }

        return null;
    }

}
