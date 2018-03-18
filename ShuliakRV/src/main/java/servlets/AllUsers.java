package servlets;

import spring.exception.AppException;
import spring.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/allusers"})
public class AllUsers extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<User> list = service.findAll();
            req.setAttribute("userlist",list);
        } catch (AppException e) {
            resp.getWriter().println(e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/pages/allusers.jsp").forward(req, resp);
    }

}
