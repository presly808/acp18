package servlets.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom HTTP servlet, which serves requests to "/show-all-users" URI.
 * Holds business logic of "display all Users' accounts" actions' "background".
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see CustomHttpServlet
 */
@WebServlet(urlPatterns = {"/show-all-users"})
public class ShowAllUsersServlet extends CustomHttpServlet {

    private static final Logger LOG = Logger.getLogger(ShowAllUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("All users: processing GET request...");

        String sessionId = req.getSession().getId();
        for (Cookie cookie: req.getCookies()) {
            if ("session_id".equals(cookie.getName()) &&
                    sessionId.equals(cookie.getValue())) {
                LOG.info("All users: found needed cookie (session_id): \"" + cookie.getValue() + "\".");
                req.getRequestDispatcher("/WEB-INF/pages/show-all-users.html").forward(req, resp);
                return;
            }
        }

        LOG.error("All users: can't find needed cookie!");
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "cookie not found");

    }
}
