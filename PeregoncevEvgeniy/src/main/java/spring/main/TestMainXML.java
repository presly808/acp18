package spring.main;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;
import spring.service.IUserService;


public class TestMainXML {

//    private static final Logger LOGGER = Logger.getLogger(TestMainXML.class);

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-Hibernate.xml");

        IUserService service = (IUserService) context.getBean("service");

        User user = new User("Borya");

        service.save(user);
        context.close();

    }
}
