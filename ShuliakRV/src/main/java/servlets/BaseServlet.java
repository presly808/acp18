package servlets;

import org.springframework.context.ApplicationContext;
import spring.exception.AppException;
import spring.model.User;
import spring.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseServlet extends HttpServlet {

    protected ApplicationContext appContext;
    protected IUserService service;

    @Override
    public void init() throws ServletException {
        appContext = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = appContext.getBean(IUserService.class);
        try {
            service.save(new User("admin","admin"));
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

}
