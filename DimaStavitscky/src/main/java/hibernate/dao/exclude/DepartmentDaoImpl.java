package hibernate.dao.exclude;

import hibernate.dao.Dao;
import hibernate.model.Department;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentDaoImpl implements Dao<Department, Integer> {
    private static final Logger LOGGER = Logger.getLogger(Dao.class);
    private EntityManagerFactory factory;
    private EntityManager manager;

    public DepartmentDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Department create(Department entity) {
        manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("can not create entity");

        } finally {
            manager.close();
        }

        return entity;
    }

    @Override
    public List<Department> findAll() {
        manager = factory.createEntityManager();
        ;
        TypedQuery<Department> query;

        try {
            query = manager.createQuery("SELECT u FROM Department u", Department.class);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public List<Department> findAll(int offset, int length) {
        manager = factory.createEntityManager();
        ;
        TypedQuery<Department> query;

        try {
            query = manager.createQuery("SELECT d FROM Department d", Department.class);
            query.setFirstResult(offset);
            query.setMaxResults(length);
            return query.getResultList();

        } finally {
            manager.close();
        }
    }

    @Override
    public Department find(Integer id) {
        manager = factory.createEntityManager();
        try {
            Department res = manager.find(Department.class, id);
            if (res == null) LOGGER.error("This department is not in the database, id: " + id);
            return res;

        } finally {
            manager.close();
        }
    }

    @Override
    public Department remove(Integer id) {
        manager = factory.createEntityManager();
        try {
            Department removedUser = manager.find(Department.class, id);
            manager.remove(id);
            return removedUser;

        } finally {
            manager.close();
        }
    }

    @Override
    public Department update(Department entity) {
        manager = factory.createEntityManager();
        Department res;
        try {
            manager.getTransaction().begin();
            res = manager.merge(entity);
            manager.getTransaction().commit();

        } finally {
            manager.close();
        }

        return res;
    }
}
