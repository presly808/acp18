package spring.dao;

import spring.model.MyUser;

/**
 * Created by serhii on 17.02.18.
 */
public interface IMyUserDao {

    MyUser create(MyUser user);

    MyUser remove(int id);

    MyUser find(int id);
}
