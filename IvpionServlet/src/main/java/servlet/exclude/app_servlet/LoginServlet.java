package servlet.exclude.app_servlet;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private ApplicationContext context;
    private UserService service;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(UserServiceImpl.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServUser user = new ServUser();
        user.setEmail(req.getParameter("email"));
        user.setPass(req.getParameter("password"));
        try {
            ServUser servUser = service.login(user.getEmail(), user.getPass());
            HttpSession session = req.getSession(true);
            session.setAttribute("inSystem", true);
            session.setAttribute("currentUserName", servUser.getName());
            resp.sendRedirect("index.jsp");
        } catch (ServletAppException e) {
            LOGGER.error(e);
            req.setAttribute("errorTitle", ServletAppException.class.getCanonicalName());
            req.setAttribute("errorMassege", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}


