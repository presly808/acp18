package hibernate.dao;


import hibernate.model.Base;
import hibernate.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoImpl <T extends Base, ID extends Base>  implements Dao <T, ID> {

    private static final Logger logger = Logger.getLogger(Dao.class);
    EntityManagerFactory factory;

    public DaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List findAll() {
        EntityManager manager = factory.createEntityManager();;
        TypedQuery<User> query;

        try {
            query = manager.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public List findAll(int offset, int length) {
        EntityManager manager = factory.createEntityManager();;
        TypedQuery<User> query;

        try {
            query = manager.createQuery("SELECT u FROM User u", User.class);
            query.setFirstResult(offset);
            query.setMaxResults(length);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public T find(ID id) {
        return null;
    }

    @Override
    public T remove(ID id) {
        return null;
    }

    @Override
    public T update(T entity) {
        return null;
    }

    /*EntityManager manager = factory.createEntityManager();
        return manager.find(base.getClass(), base);*/

    /*@Override
    public Base find(Object obj ) {
        Base base = DaoUtils.getBaseObject(obj);

        EntityManager manager = factory.createEntityManager();

        return manager.find(base.getClass(), base);
    }

    @Override
    public Base remove(Object o) {
        return null;
    }*/

}
