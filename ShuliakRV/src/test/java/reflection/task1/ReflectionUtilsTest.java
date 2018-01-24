package reflection.task1;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by serhii on 21.01.18.
 */
public class ReflectionUtilsTest {
    @Test
    public void invokeToString() throws Exception {
        String res = ReflectionUtils.invokeToString(new User("Ivan", 26, "man"));
        assertThat(res, CoreMatchers.containsString("Ivan"));
        assertThat(res, CoreMatchers.containsString("26"));
    }

    @Test
    public void convertToJson() throws Exception {
        String res = ReflectionUtils.convertToJson(new User("Ivan", 26, "man"));
        assertThat(res, CoreMatchers.containsString("Ivan"));
        assertThat(res, CoreMatchers.containsString("26"));
    }

    @Test
    public void convertToJson1() throws Exception {
        String target = "{\n" +
                "  \"name\":\"Ivan\",\n" +
                "  \"age\":26,\n" +
                "  \"sex\":\"man\"\n" +
                "}";

        User user = (User) ReflectionUtils.converFromJson(target, User.class);

        assertThat(user.name, CoreMatchers.equalTo("Ivan"));
        assertThat(user.age, CoreMatchers.equalTo(26));
        assertThat(user.sex, CoreMatchers.equalTo(null));
    }

    static class User {

        @MyField
        public String name;
        @MyField
        public int age;

        public String sex;

        public User() {
        }

        public User(String name, int age, String sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    '}';
        }
    }

}