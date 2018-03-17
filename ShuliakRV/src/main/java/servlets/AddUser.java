package servlets;

import spring.exception.AppException;
import spring.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/adduser"})
public class AddUser extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/adduser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            service.save(new User(name, password));
            resp.getWriter().println(String.format("User name: %s , password: %s added!",name,password));
        } catch (AppException e) {
            resp.getWriter().println(e.getMessage());
        }

    }
}
