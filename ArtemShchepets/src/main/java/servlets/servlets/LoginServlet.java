package servlets.servlets;

import org.springframework.context.ApplicationContext;
import servlets.model.User;
import servlets.dao.UserDao;
import servlets.dao.Dao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private ApplicationContext context;
    private Dao dao;

    @Override
    public void init() throws ServletException {
        context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        dao = context.getBean(UserDao.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            User user = new User();
            user.setName(req.getParameter("un"));
            user.setPassword(req.getParameter("pw"));

            User found = dao.findByName(user.getName());

            if (user.getName().equals(found.getName())) {
                HttpSession session = req.getSession(true);
                session.setAttribute("currentSessionUser", user);
                resp.sendRedirect("/WEB-INF/pages/userLogged.jsp"); //logged-in page
            } else
                resp.sendRedirect("/WEB-INF/pages/invalidLogin.jsp"); //error page
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }
}

