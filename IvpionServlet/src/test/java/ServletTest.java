import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import servlet.app_servlet.LoginServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletTest {

    @Mock
    private HttpServletResponse servletResponse;
    @Mock
    private HttpServletRequest servletRequest;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    private void testLogitServlet() throws ServletException, IOException {
        MockLoginServlet servlet = new MockLoginServlet();
        servlet.doPost(servletRequest, servletResponse);

    }

}
class MockLoginServlet extends LoginServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
