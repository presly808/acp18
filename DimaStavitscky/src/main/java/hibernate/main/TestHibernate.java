package hibernate.main;

import hibernate.dao.UserDao;
import hibernate.dao.exclude.UserDaoImpl;
import hibernate.exception.exclude.AppException;
import hibernate.model.User;
import hibernate.service.MainService;
import hibernate.service.MainServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.service.UserServiceImpl;

public class TestHibernate {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("hibernate");
        User user1 = new User("Ivan", 30, 2500, null, null, null, null);
        User user2 = new User("Oleg", 33, 3500, null, null, user1, null);


/*
        UserDao dao = context.getBean(UserDaoImpl.class);
*/
        MainService service = context.getBean(MainServiceImpl.class);
        try {
            service.register(user1);
            service.register(user2);
            service.getAllUsers().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
