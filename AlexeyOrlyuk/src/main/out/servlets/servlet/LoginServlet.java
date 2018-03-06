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
 * Custom HTTP servlet, which serves requests to "/login" URI.
 * Holds business logic of "Log in" actions' "background".
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see CustomHttpServlet
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends CustomHttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Log in: processing GET request...");
        req.getRequestDispatcher("/WEB-INF/pages/login-page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Log in: processing POST request...");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        IUserService userService = getApplicationContext().getBean(IUserService.class);

        try {
            User user = userService.login(email, password);

            LOG.info("Log in: authorization was successful! Authorized User: " + user + ".");

            Cookie cookie = new Cookie("session_id", req.getSession().getId());
            resp.addCookie(cookie);

            resp.sendRedirect("/show-all-users");
        } catch (AppException e) {
            LOG.error("Log in: authorization failed. Reason: \"" + e.getMessage() + "\".");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
