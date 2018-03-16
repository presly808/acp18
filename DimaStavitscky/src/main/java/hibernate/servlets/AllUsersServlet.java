package hibernate.servlets;

import hibernate.exception.exclude.AppException;
import hibernate.model.User;
import hibernate.service.MainService;
import org.springframework.context.ApplicationContext;
import spring.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/allUsers")
public class AllUsersServlet extends HttpServlet {

    private MainService service;

    @Override
    public void init() throws ServletException {
        ApplicationContext context =
                (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(MainService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<User> users = service.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/pages/all-users.jsp").forward(req, resp);

        } catch (AppException e) {
            e.printStackTrace();
            req.setAttribute("", "");
        }
    }
}
