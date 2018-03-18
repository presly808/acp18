package servlets;

import spring.exception.AppException;
import spring.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            User user = service.findByNameAndPassword(login, password);
            resp.sendRedirect("menu");
            HttpSession session = req.getSession();
            session.setAttribute("session_id", session.getId());
        } catch (AppException e) {
            resp.getWriter().println(e.getMessage());
        }

    }
}
