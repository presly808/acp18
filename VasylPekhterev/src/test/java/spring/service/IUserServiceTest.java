package spring.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.config.AppConfig;
import spring.model.User;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class IUserServiceTest {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        User user1 = new User("Test 1");
        User user2 = new User("Test 2");
        User user3 = new User("Test 3");
        iUserService.save(user1);
        iUserService.save(user2);
        iUserService.save(user3);
    }

    @Test
    public void save() {
        User user4 = new User("Test 4");
        User savedUser = iUserService.save(user4);
        assertThat(savedUser.getName(), equalTo("Test 4"));
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