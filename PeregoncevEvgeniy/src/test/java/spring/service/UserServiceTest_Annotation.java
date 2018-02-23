package spring.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.model.User;
import static org.junit.Assert.*;

public class UserServiceTest_Annotation {
    private IUserService service;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext("spring");
        service = context.getBean(UserService.class);
        testUser = new User("Kira");
    }

    @Test
    public void save() {
        service.save(testUser);
        User expected = service.findById(2);
        assertNotNull(expected);
    }

    @Test
    public void deleteById() {
        service.deleteById(1);
        assertNull(service.findById(1));
    }
}