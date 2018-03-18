package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.model.User;
import spring.service.IUserService;

/**
 * Created by serhii on 18.02.18.
 */
public class RunAnnotationContext {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("spring");
        IUserService iUserService = applicationContext.getBean(IUserService.class);

        User user = new User("Test User");
        User savedUser = iUserService.save(user);

        System.out.println(savedUser.getName());
    }
}
