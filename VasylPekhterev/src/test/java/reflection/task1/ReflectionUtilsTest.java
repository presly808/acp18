package reflection.task1;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
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

    @Test
    public void convertFromJson() throws Exception {
        String target = "{\n" +
                "  \"name\":\"Ivan\",\n" +
                "  \"age\":26,\n" +
                "  \"pass\":\"pwd\"\n" +
                "}";

        Admin admin = (Admin) ReflectionUtils.converFromJson(target, Admin.class);

        assertThat(admin.name, CoreMatchers.equalTo("Ivan"));
        assertThat(admin.age, CoreMatchers.equalTo(26));
        assertNull(admin.pass);
    }

}