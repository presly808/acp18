package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by serhii on 18.02.18.
 */
public class RunAnnotationContext {
    public static void main(String[] args) {
        ApplicationContext xmlApplicationContext = new AnnotationConfigApplicationContext("spring");
        IUserService iUserService = xmlApplicationContext.getBean(IUserService.class);

    }
}
