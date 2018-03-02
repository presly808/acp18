package servlet.app_servlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import servlet.service.UserService;
import servlet.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class InitServlet extends HttpServlet {

    protected ApplicationContext context;
    protected UserService service;
    protected static final Logger LOGGER = Logger.getLogger(InitServlet.class);

    @Override
    public void init() throws ServletException {
        context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(UserServiceImpl.class);
    }


}
