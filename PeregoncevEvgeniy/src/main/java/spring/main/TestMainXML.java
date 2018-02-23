package spring.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.model.User;
import spring.service.IUserService;


public class TestMainXML {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring-Hibernate.xml");

        IUserService service = (IUserService) context.getBean("service");

        User user = new User("Borya");

        service.deleteById(125);
        service.save(user);

        User findUser = service.findById(110);

        System.out.println("found user is "+findUser.toString());
        context.close();

    }
}
