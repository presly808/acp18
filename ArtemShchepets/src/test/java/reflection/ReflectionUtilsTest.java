package reflection;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtilsTest {
    @Test
    public void invokeToString() throws Exception {
        String res = ReflectionUtils.invokeToString(new User("Ivan", 26));
        assertThat(res, CoreMatchers.containsString("Ivan"));
        assertThat(res, CoreMatchers.containsString("26"));
    }

    @Test
    public void convertToJson() throws Exception {
        String res = ReflectionUtils.convertToJson(new User("Ivan", 26));
        assertThat(res, CoreMatchers.containsString("Ivan"));
        assertThat(res, CoreMatchers.containsString("26"));
    }

    @Test
    public void convertToJson1() throws Exception {
        String target = "{\n" +
                "  \"name\":\"Ivan\",\n" +
                "  \"age\":26\n" +
                "}";

        User user = (User) ReflectionUtils.converFromJson(target, User.class);

        assertThat(user.name, CoreMatchers.equalTo("Ivan"));
        assertThat(user.age, CoreMatchers.equalTo(26));

    }


    static class User {

        @MyField
        public String name;
        @MyField
        public int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public User() {
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}