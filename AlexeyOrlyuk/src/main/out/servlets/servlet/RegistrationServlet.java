package servlets.servlet;

import org.apache.log4j.Logger;
import servlets.exception.AppException;
import servlets.model.User;
import servlets.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom HTTP servlet, which serves requests to "/registration" URI.
 * Holds business logic of "Sign up" actions' "background".
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see CustomHttpServlet
 */
@WebServlet(urlPatterns = {"/registration"})
public class RegistrationServlet extends CustomHttpServlet {

    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Sign up: processing GET request...");
        req.getRequestDispatcher("/WEB-INF/pages/registration-page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Sign up: processing POST request...");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        IUserService userService = getApplicationContext().getBean(IUserService.class);

        try {
            User user = userService.register(email, password);

            LOG.info("Sign up: registration was successful! Authorized User: " + user + ".");

            resp.addCookie(new Cookie("session_id", req.getSession().getId()));

            resp.sendRedirect("/show-all-users");
        } catch (AppException e) {
            LOG.error("Sign up: registration failed. Reason: \"" + e.getMessage() + "\".");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
