package spring.main;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.DAO.IUserDao;
import spring.DAO.UserDao;
import spring.model.User;
import spring.service.IUserService;
import spring.service.UserService;

public class TestMainAnnotations {

private static ApplicationContext context;
    private static IUserService service;


    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext("spring");

        service = context.getBean(UserService.class);

        User user = new User();
        user.setName("Aspirin");
        service.save(user);

    }






}
