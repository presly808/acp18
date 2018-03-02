package servlet.app_servlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import servlet.exception.ServletAppException;
import servlet.model.ServUser;
import servlet.service.UserService;
import servlet.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private ApplicationContext context;
    private UserService service;
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

    @Override
    public void init() throws ServletException {
        context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(UserServiceImpl.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //some action
        //redirect to register.jsp
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServUser user = new ServUser();
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setPass(req.getParameter("password"));
        try {
            ServUser servUser = service.addUser(user);
            req.setAttribute("user", servUser);
            req.getRequestDispatcher("/WEB-INF/pages/user-info.jsp").forward(req, resp);
        } catch (ServletAppException e) {
            LOGGER.error(e);
            req.setAttribute("errorTitle", ServletAppException.class.getCanonicalName());
            req.setAttribute("errorMassege", e.getMessage());
            req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}

