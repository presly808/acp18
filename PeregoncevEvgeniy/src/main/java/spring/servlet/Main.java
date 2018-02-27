package spring.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/")
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        pw.println("<a href=\"http://localhost:8080/app/login\"><h1>Login page</h1></a>");
        pw.println("<a href=\"http://localhost:8080/app/addUser\"><h1>addUser page</h1></a>");
        pw.println("<a href=\"http://localhost:8080/app/showAll\"><h1>showAll page</h1></a>");
    }
}
