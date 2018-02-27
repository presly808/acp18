package spring.servlet;


import org.springframework.context.ApplicationContext;
import spring.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/showAll")
public class ShowUsers extends HttpServlet {

    private UserService service;

    @Override
    public void init() {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List list = service.getAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("ShowAll.jsp").forward(req, resp);

    }

}


