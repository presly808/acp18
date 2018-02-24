package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainXML {

    public static void main(String[] args) {

        /*
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-context.xml");

        IUserService userService = (IUserService) context.getBean("userServiceImpl");
        userService.save(new User(""));
        System.out.println("\n\n\n\n");
*/


        ApplicationContext context1 = new AnnotationConfigApplicationContext("spring");
        UserServiceImpl userService1 = (UserServiceImpl) context1.getBean("userServiceImpl");
        userService1.save(new User("testio222"));


    }
}
