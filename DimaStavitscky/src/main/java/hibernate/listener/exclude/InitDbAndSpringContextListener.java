package hibernate.listener.exclude;

import hibernate.exception.exclude.AppException;
import hibernate.service.MainService;
import hibernate.utils.exclude.DbUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitDbAndSpringContextListener implements ServletContextListener {
    private static final Logger LOGGER =
            Logger.getLogger(InitDbAndSpringContextListener.class);

    private MainService service;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("hibernate");
        service = context.getBean(MainService.class);
        sce.getServletContext().setAttribute("spring-context", context);

        try {
            DbUtils.AddTestInfoToDB(service);
        } catch (AppException e) {
            LOGGER.error("Test db didn't create");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        service.dropAllTables();
    }
}
