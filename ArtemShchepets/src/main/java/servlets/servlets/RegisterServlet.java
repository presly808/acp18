package servlets.servlets;

import org.springframework.context.ApplicationContext;
import servlets.dao.Dao;
import servlets.dao.UserDao;
import servlets.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private ApplicationContext context;
    private Dao<User, Integer> dao;

    @Override
    public void init() throws ServletException {
        context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        dao = context.getBean(UserDao.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = new User();
            user.setName(req.getParameter("un"));
            user.setPassword(req.getParameter("pw"));
            user.setAge((Integer) req.getAttribute("age"));

            User newUser = dao.create(user);

            HttpSession session = req.getSession(true);
            session.setAttribute("currentSessionUser", user);
            resp.sendRedirect("userLogged.jsp"); //logged-in page

        } catch (Throwable theException) {
            System.out.println(theException);
            resp.sendRedirect("invalidLogin.jsp"); //error page
        }
    }

}
