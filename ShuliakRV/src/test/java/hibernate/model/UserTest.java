package hibernate.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UserTest {

    private User user;

    @Before
    public void createUser() {
        user = new User();
        User user1 = new User(1,"Petya",20);
        User user2 = new User (20,2000,null,null);
    }

    @Test
    public void getAge() throws Exception {
        assertEquals(0,user.getAge());
    }

    @Test
    public void setAge() throws Exception {
        user.setAge(20);
        assertEquals(20,user.getAge());
    }

    @Test
    public void getSalary() throws Exception {
        assertEquals(0,user.getSalary(),0.001);
    }

    @Test
    public void setSalary() throws Exception {
        user.setSalary(200);
        assertEquals(200,user.getSalary(),0.001);
    }

    @Test
    public void getLocalDateTime() throws Exception {
        assertNull(user.getLocalDateTime());
    }

    @Test
    public void setLocalDateTime() throws Exception {
        user.setLocalDateTime(LocalDateTime.of(2018,02,28,0,0,0));
        LocalDateTime localDateTime = LocalDateTime.of(2018,02,28,0,0,0);
        assertTrue(localDateTime.equals(user.getLocalDateTime()));
    }

    @Test
    public void toStringUser() throws Exception {
        user.setName("Vasya");
        assertTrue(user.toString().contains("Vasya"));
    }

}