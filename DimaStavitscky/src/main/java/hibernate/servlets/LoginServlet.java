package hibernate.servlets;

import hibernate.model.User;
import hibernate.service.MainService;
import org.springframework.context.ApplicationContext;
import spring.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private MainService service;

    @Override
    public void init() throws ServletException {
        ApplicationContext context =
                (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = (MainService) context.getBean(UserServiceImpl.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        try {
            String loggedUserName = service.login(login, pass).getName();
            HttpSession session = req.getSession();

            session.setAttribute("name", loggedUserName);
            resp.getWriter().print("User " + loggedUserName + " successfully logged in");

        } catch (IllegalArgumentException e) {
            resp.getWriter().print("Wrong login or password");

        } finally {
            resp.getWriter().flush();
        }

        req.getRequestDispatcher("/WEB-INF/home-page.jsp").forward(req, resp);
    }
}
