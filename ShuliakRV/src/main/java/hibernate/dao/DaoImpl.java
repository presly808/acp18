package hibernate.dao;

import org.springframework.orm.jpa.EntityManagerFactoryAccessor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoImpl<T, ID> implements Dao<T, ID> {

    private EntityManagerFactory factory;

    private T obj;

    private Class<T> clazz = (Class<T>) obj.getClass();

    public DaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<T> findAll() {

        EntityManager manager = factory.createEntityManager();
        TypedQuery<T> query = manager.createQuery("SELECT e FROM " +
                clazz.getSimpleName()+" e", clazz);
        List<T> list = query.getResultList();
        manager.close();

        return list;
    }

    @Override
    public List<T> findAll(int offset, int length) {

        EntityManager manager = factory.createEntityManager();
        TypedQuery<T> query = manager.createQuery("SELECT e FROM " +
                clazz.getSimpleName()+" e", clazz);
        List<T> list = query.getResultList();

        query.setFirstResult(offset);
        query.setMaxResults(offset+length);
        manager.close();

        return list;
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
}
