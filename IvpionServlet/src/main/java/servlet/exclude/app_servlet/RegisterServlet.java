package servlet.exclude.app_servlet;

import org.apache.log4j.Logger;
import servlet.exception.ServletAppException;
import servlet.model.ServUser;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends InitServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //some action
        //redirect to register.jsp
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Register new user...");
        ServUser user = new ServUser(
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("password"));

        try {
            ServUser servUser = getService().addUser(user);
            req.setAttribute("user", servUser);
            LOGGER.info("Register successful");
            req.getRequestDispatcher("/WEB-INF/pages/user-info.jsp").forward(req, resp);
        } catch (ServletAppException e) {
            LOGGER.error(e);
            req.setAttribute("errorTitle", ServletAppException.class.getCanonicalName());
            req.setAttribute("errorMassege", e.getMessage());
            req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}

