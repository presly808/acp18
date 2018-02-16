package hibernate.dao;

import hibernate.model.Department;
import hibernate.model.User;
import hibernate.wrap.TransactionWrapper;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao{

    private static final Logger LOGGER = Logger.getLogger(DepartmentDaoImpl.class);

    private EntityManagerFactory factory;

    public DepartmentDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Department> findAll() {
        LOGGER.info("start find all Departments");
        return TransactionWrapper.wrap(factory, new Department(), -1)
                .executeWithTransaction((manager, entity, limit) ->
                manager.createQuery("select d from Department d", Department.class)
                        .setMaxResults(limit).getResultList());
    }

    @Override
    public List<Department> findAll(int offset, int length) {
        LOGGER.info("start find departments with limit");
        return TransactionWrapper.wrap(factory, new Department(), length).execute((manager, entity, limit) ->
                manager.createQuery("select u from Department u", Department.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

    @Override
    public Department find(Integer integer) {
        LOGGER.info("search department by id: " + integer);
        return TransactionWrapper.wrap(factory, new Department()).execute((manager, entity) -> {
           return manager.find(entity.getClass(), integer);
        });
    }

    @Override
    public Department remove(Integer integer) {
        LOGGER.info("deleting department with id: " + integer);
        return TransactionWrapper.wrap(factory, new Department()).executeWithTransaction((manager, entity) -> {
            Department old = manager.find(entity.getClass(), integer);
            manager.remove(old);
            return old;
        });
    }

    @Override
    public Department create(Department obj) {
        LOGGER.info("create and save new Department");
        TransactionWrapper.wrap(factory, obj).executeWithTransaction(EntityManager::persist);

        LOGGER.info("new Department was successfully created");
        return TransactionWrapper.wrap(factory, obj)
                .execute((manager, wrapEntity) -> {
                    return manager.find(wrapEntity.getClass(), wrapEntity.getId());
                });
    }

    @Override
    public Department update(Department entity) {
        LOGGER.info("start update department with id: " +entity.getId());
        return TransactionWrapper.wrap(factory, new Department()).executeWithTransaction((manager, entity1) -> {
            manager.find(entity1.getClass(), entity1.getId());
            return manager.merge(entity1);
        });
    }
}
