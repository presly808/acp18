package hibernate.dao;

import hibernate.model.User;

import javax.persistence.EntityManagerFactory;

public class UserDao extends DaoImpl<User,Integer> {

    public UserDao(EntityManagerFactory factory) {
        super(factory);
    }


}
