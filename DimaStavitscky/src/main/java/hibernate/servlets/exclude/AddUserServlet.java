package hibernate.servlets.exclude;

import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.service.MainService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "/addUser")
public class AddUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddUserServlet.class);

    private MainService service;

    @Override
    public void init() throws ServletException {
        ApplicationContext context =
                (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(MainService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/add-users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // initialize user fields
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        double salary = Double.parseDouble(req.getParameter("salary"));
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        Department department;
        City city;
        User manager;

        try {
            department = service.findDepartmentByName(req.getParameter("department"));
            city = service.findCityByName(req.getParameter("city"));
            manager = service.findById(
                    Integer.parseInt(req.getParameter("managerId")));

            User newUser = new User(name, age, salary, login,
                    pass, department, city, manager, LocalDateTime.now());
            newUser = service.register(newUser);

            LOGGER.info("Added user: " + newUser);
            req.setAttribute("result", "User successful added: " + newUser);

            req.getRequestDispatcher("/WEB-INF/pages/add-users.jsp").forward(req, resp);

        } catch (AppException e) {
            req.setAttribute("result", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}
