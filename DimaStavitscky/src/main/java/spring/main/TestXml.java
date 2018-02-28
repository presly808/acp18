package spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;
import spring.service.IUserService;
import spring.service.UserServiceImpl;

public class TestXml {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-context.xml");

        IUserService userService = (IUserService) context.getBean("userService");
        User user = new User("Dima");
        User res = userService.save(user);
        System.out.println(res);
    }
}
