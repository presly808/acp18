package servlets.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom HTTP servlet, which serves requests to "/index" URI.
 * Holds business logic of index page "background".
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see CustomHttpServlet
 */
@WebServlet(urlPatterns = {"/index"})
public class IndexServlet extends CustomHttpServlet {

    private static final Logger LOG = Logger.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Index: processing GET request...");
        req.getRequestDispatcher("/WEB-INF/pages/index-page.jsp").forward(req, resp);
    }

}
