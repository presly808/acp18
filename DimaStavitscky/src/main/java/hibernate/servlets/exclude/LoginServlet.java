package hibernate.servlets.exclude;

import hibernate.service.MainService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    private MainService service;

    @Override
    public void init() throws ServletException {
        ApplicationContext context =
                (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(MainService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        try {
            String loggedUserName = service.login(login, pass).getName();
            HttpSession session = req.getSession();

            session.setAttribute("name", loggedUserName);
            req.setAttribute("result","User " + loggedUserName + " successfully logged in");

            LOGGER.info(String.format("User %s successfully logged in, time: %s",
                    loggedUserName, LocalDateTime.now().toString()));
            req.getRequestDispatcher("/WEB-INF/pages/home-page.jsp").forward(req, resp);

        } catch (NoResultException e) {
            LOGGER.error("Wrong login or password, time: " +
                    LocalDateTime.now().toString());
            req.setAttribute("result", "Wrong login or password");

            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);

        }

    }
}
