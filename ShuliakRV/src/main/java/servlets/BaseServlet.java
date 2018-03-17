package servlets;

import org.springframework.context.ApplicationContext;
import spring.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseServlet extends HttpServlet {

    private ApplicationContext appContext;

    @Override
    public void init() throws ServletException {
        appContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }

    public IUserService getService() {
        return getAppContext().getBean(IUserService.class);
    }
}
