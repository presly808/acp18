package spring.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

    }
}
