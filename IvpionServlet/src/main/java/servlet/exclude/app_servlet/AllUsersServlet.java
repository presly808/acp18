package servlet.exclude.app_servlet;

import servlet.model.ServUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/all-users"})
public class AllUsersServlet extends InitServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ServUser> users = service.allUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/pages/all-users.jsp").forward(req,resp);
    }
}
