package spring.servlet;

import org.springframework.context.ApplicationContext;
import spring.model.User;
import spring.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/addUser")
public class AddUser extends HttpServlet {

    private UserService service;

    @Override
    public void init(){
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("AddUser.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = (req.getParameter("name"));
        String password = (req.getParameter("password"));

        User user = new User(name,password);
        service.save(user);
        System.out.println(user.toString());
        PrintWriter pw = resp.getWriter();
        pw.println("User added");
    }
}


