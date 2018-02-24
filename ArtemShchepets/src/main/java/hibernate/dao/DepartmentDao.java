package hibernate.dao;

import hibernate.model.Department;
import hibernate.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentDao implements Dao<Department, Integer> {
    private EntityManagerFactory factory;

    public DepartmentDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List findAll() {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> query = manager.createQuery("SELECT * FROM departments", User.class);

            return query.getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List findAll(int offset, int length) {
        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> query = manager.createQuery("SELECT * FROM departments", User.class);
            query.setMaxResults(length);
            query.setFirstResult(offset);

            return query.getResultList();
        } finally {
            manager.close();
        }

    }

    @Override
    public Department find(Integer integer) {

        EntityManager manager = factory.createEntityManager();

        try {

            TypedQuery<User> allUsers = manager.createQuery("SELECT * FROM departments", User.class);

            return manager.find(Department.class, integer);
        } finally {
            manager.close();
        }
    }

    @Override
    public Department remove(Integer integer) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            Department department = manager.find(Department.class, integer);
            manager.remove(department);
            transaction.commit();
            return department;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public Department update(Department entity) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            Department department = manager.find(Department.class, entity.getId());
            Department oldDepartment = (Department) department.clone();
            manager.merge(department);
            transaction.commit();
            return oldDepartment;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public Department create(Department entity) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            manager.close();
        }

    }
}
