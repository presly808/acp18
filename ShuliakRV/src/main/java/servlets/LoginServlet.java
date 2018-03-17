package servlets;

import db.model.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import spring.exception.AppException;
import spring.model.User;
import spring.service.IUserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        try {
            User user = getService().findByNameAndPassword(login,password);
            req.getRequestDispatcher("/WEB-INF/pages/menu.jsp").forward(req,resp);

        } catch (AppException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }


    }
}
