package hibernate.dao;


import db.model.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class DaoDepartmentImpl implements Dao {

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
    public Object create(Object entity) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try{
            transaction.begin();
            manager.persist(city);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            manager.close();
        }
        return null;
    }
}
