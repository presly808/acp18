package hibernate.dao;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DaoImpl implements Dao {

    private static final Logger logger = Logger.getLogger(Dao.class);
    EntityManagerFactory factory;

    public DaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List findAll() {
        /*EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            manager.;
        } finally {
            manager.close();
        }*/
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
