package spring;

/**
 * Created by serhii on 17.02.18.
 */
public interface IUserService {

    User save(User user);
    User delete(int id);
    User findById(int id);

}
