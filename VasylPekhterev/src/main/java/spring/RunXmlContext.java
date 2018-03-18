package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;
import spring.service.IUserService;
import spring.service.UserServiceImpl;

/**
 * Created by serhii on 18.02.18.
 */
public class RunXmlContext {

    public static void main(String[] args) {
        ApplicationContext xmlApplicationContext =
                new ClassPathXmlApplicationContext("/spring-context.xml");

        IUserService iUserService = xmlApplicationContext.getBean(UserServiceImpl.class);

        User user = new User("Test User");
        User savedUser = iUserService.save(user);

        System.out.println(savedUser.getName());

    }
}
