package hibernate.dao;

import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoImpl<T, ID> implements Dao<T, ID> {

    private final EntityManager manager;
    private final Class<T> clazz;

    public DaoImpl(EntityManager manager, Class<T> clazz) {
        this.manager = manager;
        this.clazz = clazz;
    }

    @Override
    public T create(T entity) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = manager.createNamedQuery("selectFrom" + clazz.getSimpleName(), clazz);
        return query.getResultList();
    }

    @Override
    public List<T> findAll(int offset, int length) {
        TypedQuery<T> query = manager.createNamedQuery("selectFrom" + clazz.getSimpleName(), clazz).setFirstResult(offset).setMaxResults(length);
        return query.getResultList();
    }

    @Override
    public T find(ID id) {
        return manager.find(clazz, id);
    }

    @Override
    public T remove(ID id) {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            T entity = manager.find(clazz, id);
            manager.remove(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T update(T entity) {

        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            T found = null;
            if (User.class == entity.getClass()) {
                User user = (User) entity;
                found = manager.find(clazz, user.getId());
                if (user.getCity() != null && user.getCity().getId() == 0) {
                    manager.persist(user.getCity());
                }
                if (user.getDepartment() != null && user.getDepartment().getId() == 0) {
                    manager.persist(user.getDepartment());
                }
                if (user.getManage() != null && user.getManage().getId() == 0) {
                    manager.persist(user.getManage());
                }
                manager.merge(user);
            } else if (City.class == entity.getClass()){
                found = manager.find(clazz, ((City)entity).getId());
                manager.merge(entity);
            } else if (Department.class == entity.getClass()) {
                found = manager.find(clazz, ((Department) entity).getId());
                manager.merge(entity);
            }
            transaction.commit();
            return found;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeAll() {
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            String hql = "DELETE FROM " + clazz.getSimpleName();
            Query query = manager.createQuery(hql);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<T> findByName(String name) {
        TypedQuery<T> query = manager.createNamedQuery("findByName" + clazz.getSimpleName(), clazz);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<T> findInRange(double minSal, double maxSal) {
        TypedQuery<T> query = manager.createNamedQuery("findBySalaryUser", clazz);
        query.setParameter("minSalary", minSal);
        query.setParameter("maxSalary", maxSal);
        return query.getResultList();
    }

    @Override
    public List<T> findByDate(LocalDateTime start, LocalDateTime end) {
        TypedQuery<T> query = manager.createNamedQuery("findByDateUser", clazz);
        query.setParameter("sDate", start);
        query.setParameter("eDate", end);
        return query.getResultList();
    }

    @Override
    public Map<Department,Double> getAvgSalaryGroupByDepartment() {
        Query query = manager.createNamedQuery("avgSalaryGroupByDepartmentUser");
        List<Object[]> results = query.getResultList();
        Map<Department,Double> map = new HashMap<>();
        for (Object[] result:results) {
            map.put((Department) result[0], (Double) result[1]);
        }
        return map;
    }
}
