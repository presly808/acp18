package servlets.listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Custom Servlet Context Listener class.
 * Adds ApplicationContext instance to current ServletContext.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see ServletContextListener
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("servlets");
        sce.getServletContext().setAttribute("applicationContext", applicationContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        GenericApplicationContext applicationContext =
                (GenericApplicationContext) sce.getServletContext().getAttribute("applicationContext");
        applicationContext.close();
    }
}
