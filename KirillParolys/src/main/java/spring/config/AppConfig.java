package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.dao.UserDaoImpl;
import spring.model.User;
import spring.service.UserServiceImpl;


@Configuration
public class AppConfig {

    @Bean(name = "user")
    public User user() {
        return new User(1, "Kirill");
    }

    @Bean
    public UserDaoImpl userDao() {
        return new UserDaoImpl(user());
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userDao());
    }


}
