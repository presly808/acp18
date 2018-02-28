package hibernate.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.service.UserServiceImpl;

public class TestHibernate {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("hibernate");

    }
}
