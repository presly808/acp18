package servlets.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.exception.AppException;
import spring.model.User;
import spring.service.IUserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitSpringContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new AnnotationConfigApplicationContext("spring");
        sce.getServletContext().setAttribute("spring-context", context);
        IUserService service = context.getBean(IUserService.class);
        try {
            service.save(new User("admin","admin"));
        } catch (AppException e) {
            e.printStackTrace();
        }
    }
}
