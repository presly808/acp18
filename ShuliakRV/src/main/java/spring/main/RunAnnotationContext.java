package spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.exception.AppException;
import spring.model.User;
import spring.service.IUserService;

/**
 * Created by serhii on 18.02.18.
 */
public class RunAnnotationContext {
    public static void main(String[] args) {
        ApplicationContext annotationApplicationContext =
                new AnnotationConfigApplicationContext("spring");
        IUserService iUserService =
                annotationApplicationContext.getBean(IUserService.class);

        try {
            User user = iUserService.save(new User("Vasya"));
            System.out.println(iUserService.findAll());
        } catch (AppException e) {
            e.printStackTrace();
        }

    }
}
