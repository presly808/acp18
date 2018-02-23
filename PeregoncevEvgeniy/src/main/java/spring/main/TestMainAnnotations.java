package spring.main;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.model.User;
import spring.service.IUserService;
import spring.service.UserService;

public class TestMainAnnotations {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("spring");

        IUserService service = context.getBean(UserService.class);

        User user = new User();
        user.setName("Aspirin");
        service.save(user);
        service.deleteById(126);

        User findUser = service.findById(110);
        System.out.println(findUser.toString());

    }


}
