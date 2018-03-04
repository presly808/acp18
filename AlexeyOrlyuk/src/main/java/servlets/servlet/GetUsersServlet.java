package servlets.servlet;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.log4j.Logger;
import servlets.exception.AppException;
import servlets.model.User;
import servlets.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.List;

/**
 * Custom HTTP servlet, which serves requests to "/get-users" URI.
 * Holds business logic of AJAX "get all users' list" request "background".
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see CustomHttpServlet
 */
@WebServlet(urlPatterns = {"/get-users"})
public class GetUsersServlet extends CustomHttpServlet {

    private static final Logger LOG = Logger.getLogger(GetUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Get users: processing GET request..");

        IUserService userService = getApplicationContext().getBean(IUserService.class);

        try {
            List<User> userList = userService.listAllRegisteredUsers();

            LOG.info("Get users: listing of all Users was successful!");

            resp.getWriter().println(userList);
        } catch (AppException e) {
            LOG.error("Get users: listing of all Users failed! Reason: \"" + e.getMessage() + "\".");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
