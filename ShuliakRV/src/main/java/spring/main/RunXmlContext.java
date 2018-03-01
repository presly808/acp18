package spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.exception.AppException;
import spring.model.User;
import spring.service.IUserService;

/**
 * Created by serhii on 18.02.18.
 */
public class RunXmlContext {

    public static void main(String[] args) {
        ApplicationContext xmlApplicationContext =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        IUserService iUserService = xmlApplicationContext.getBean(IUserService.class);

        try {
            User user = iUserService.save(new User("Vasya"));
            System.out.println(iUserService.findAll());
        } catch (AppException e) {
            e.printStackTrace();
        }

    }
}
