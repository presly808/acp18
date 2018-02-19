package spring;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by serhii on 17.02.18.
 */
@Component
public interface IUserService {


    @Autowired
    User save(User user);
    User delete(int id);
    User findById(int id);


}
