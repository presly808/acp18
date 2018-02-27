package spring.servlet.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.service.IUserService;
import spring.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyInitSpringContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ApplicationContext context = new AnnotationConfigApplicationContext("spring");
        IUserService service = context.getBean(UserService.class);

        sce.getServletContext().setAttribute("spring-context",context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
