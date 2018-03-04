package servlets.servlet;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Custom abstract HTTP Servlet entity.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see HttpServlet
 */
public abstract class CustomHttpServlet extends HttpServlet {

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void init() throws ServletException {
        applicationContext = (ApplicationContext) getServletContext().getAttribute("applicationContext");
    }

}
