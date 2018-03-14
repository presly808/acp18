package spring.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.model.User;

import javax.persistence.EntityManagerFactory;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class IUserServiceTest {

    private IUserService iUserService;
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext("spring");
        iUserService = applicationContext.getBean(IUserService.class);
        User user1 = new User("Test 1");
        User user2 = new User("Test 2");
        User user3 = new User("Test 3");
        iUserService.save(user1);
        iUserService.save(user2);
        iUserService.save(user3);
    }

    @After
    public void tearDown() {
        EntityManagerFactory factory = (EntityManagerFactory) applicationContext.getBean("entityManagerFactoryBean");
        factory.close();
    }

    @Test
    public void save() {
        User user4 = new User("Test 4");
        User savedUser = iUserService.save(user4);
        assertThat(savedUser.getName(), equalTo("Test 4"));
        assertThat(savedUser.getId(), equalTo(4));
    }

    @Test
    public void delete() {
        User removedUser = iUserService.delete(1);
        assertThat(removedUser.getName(), equalTo("Test 1"));
        assertThat(removedUser.getId(), equalTo(1));
    }

    @Test
    public void findById() {
        User foundUser = iUserService.findById(2);
        assertThat(foundUser.getName(), equalTo("Test 2"));
        assertThat(foundUser.getId(), equalTo(2));
    }
}