package hibernate.dao;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class DaoImpl implements Dao {

    private static final Logger logger = Logger.getLogger(Dao.class);
    EntityManager manager;

    public DaoImpl(EntityManager manager) {
        this.manager = manager;
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
}
