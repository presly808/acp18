package reflection.task1.Test;

import reflection.task1.ReflectionUtils;
import reflection.task1.model.Person;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        System.out.println(ReflectionUtils.invokeToString(new Person("Nazar", 25)));
        System.out.println(ReflectionUtils.convertToJson(new Person("Nazar", 25, 10000, "John")));

        StringBuilder sb = new StringBuilder();

        String json = sb.append("{").append("\"name\":\"Nazar\",").append("\"age\":\"34\"").append("}").toString();

        System.out.println(ReflectionUtils.converFromJson(json,Person.class).toString());
    }
}
