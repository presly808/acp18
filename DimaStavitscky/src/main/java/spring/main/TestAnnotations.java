package spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.aspect.Aspects;
import spring.model.User;
import spring.service.IUserService;

public class TestAnnotations {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("spring");

        IUserService service = (IUserService) context.getBean("userService");

        User user = new User("Diman");
        User savedUser = service.save(user);
        System.out.println(savedUser);
    }
}
