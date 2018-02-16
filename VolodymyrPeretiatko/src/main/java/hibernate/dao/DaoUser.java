package hibernate.dao;

import hibernate.model.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DaoUser implements Dao<User, Integer> {

    private EntityManagerFactory factory;

    public DaoUser(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public User create(User entity) {
        return (User) DaoUtilH2Db.create(entity, factory);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) DaoUtilH2Db.findAll(User.class, factory);
    }

    @Override
    public List<User> findAll(int offset, int length) {
        return (List<User>) DaoUtilH2Db.findAll(User.class, factory, offset, length);
    }

    @Override
    public User find(Integer id) {
        return (User) DaoUtilH2Db.find(User.class, id, factory);
    }

    @Override
    public User remove(Integer id) {
        return (User) DaoUtilH2Db.remove(User.class, id, factory);
    }

    @Override
    public User update(User entity) {
        return (User) DaoUtilH2Db.update(User.class, entity, factory);
    }

    @Override
    public boolean removeAll() {
        return DaoUtilH2Db.removeAll(User.class, factory);
    }
}
