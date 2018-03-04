package servlet.exclude.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitSpringContextListener  implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new AnnotationConfigApplicationContext("servlet");
        sce.getServletContext().setAttribute("spring-context", context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}