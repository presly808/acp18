package servlet.exclude.app_servlet;

import org.apache.log4j.Logger;
import servlet.model.ServUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/all-users"})
public class AllUsersServlet extends InitServlet {

    private static final Logger LOGGER = Logger.getLogger(AllUsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Start search all users...");
        List<ServUser> users = getService().allUsers();
        req.setAttribute("users", users);
        LOGGER.info("Forward to front");
        req.getRequestDispatcher("/WEB-INF/pages/all-users.jsp").forward(req,resp);
    }
}
