package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.service.IUserService;

/**
 * Created by serhii on 18.02.18.
 */
public class RunXmlContext {

    public static void main(String[] args) {
        ApplicationContext xmlApplicationContext =
                new ClassPathXmlApplicationContext("/META-INF/spring-context.xml");
        IUserService iUserService = xmlApplicationContext.getBean(IUserService.class);

    }
}
