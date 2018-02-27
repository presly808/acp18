package spring.servlet;

import org.springframework.context.ApplicationContext;
import spring.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet {

    private UserService service;

    @Override
    public void init() {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("spring-context");
        service = context.getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = (req.getParameter("name"));
        String password = (req.getParameter("password"));
        List list = service.getAll();


        for (Object aList : list) {
            try {
                String a = (String) aList.getClass().getMethod("getName").invoke(aList);
                String b = (String) aList.getClass().getMethod("getPassword").invoke(aList);
                if (name.equals(a) && password.equals(b)) {

                    System.out.println("user detected with id " + aList.getClass().getMethod("getId").invoke(aList));
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
            PrintWriter pw = resp.getWriter();
            pw.println("Hello"+name);
    }
}
