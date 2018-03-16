package spring.exclude.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.exclude.model.User;
import spring.exclude.service.IUserService;

public class TestAnnotations {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("spring");

        IUserService service = (IUserService) context.getBean("userService");

        User user = new User("Diman");
        User savedUser = service.save(user);
        System.out.println(savedUser);
    }
}
