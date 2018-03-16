package spring.exclude.dao;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private EntityManagerFactory factory;

    public UserDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Object create(Object entity) {
        return factory.createEntityManager().merge(entity);
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List findAll(int offset, int length) {
        return null;
    }

    @Override
    public Object find(Object o) {
        return null;
    }

    @Override
    public Object remove(Object o) {
        return null;
    }

    @Override
    public Object update(Object entity) {

        return null;
    }

    @Override
    public Object deleteTable() {
        return null;
    }
}
