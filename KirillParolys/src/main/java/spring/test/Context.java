package spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import spring.service.UserService;

import java.io.File;

/**
 * Created by serhii on 18.02.18
 */
public class Context {
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private ApplicationContext applicationContext;

    public Context(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
