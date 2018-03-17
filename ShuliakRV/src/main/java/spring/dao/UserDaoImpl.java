package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.model.User;

import javax.persistence.*;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    //@Autowired
    //private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public List<User> findAll() {

        //EntityManager manager = factory.createEntityManager();
        TypedQuery<User> query = manager.createQuery("SELECT e FROM spring.model.User e", User.class);

        //try {
        List<User> list = query.getResultList();
        return list;
        //} catch (Exception e) {
        //    return null;
        //} finally {
        //manager.close();
        //}
    }

    @Transactional
    @Override
    public User find(int id) {

        //EntityManager manager = factory.createEntityManager();

        //try {
        return manager.find(User.class, id);
        //} catch (Exception e) {
        //    return null;
        //} //finally {
        //manager.close();
        //}

    }

    @Transactional
    public User findByNameAndPassword(String name, String password) {

        //EntityManager manager = factory.createEntityManager();
        TypedQuery<User> query = manager.createQuery("SELECT e FROM spring.model.User e " +
                "WHERE e.name = :name " +
                "AND e.password = :password", User.class);
        query.setParameter("name", name);
        query.setParameter("password", password);
        //try {
        return query.getSingleResult();
        //} catch (Exception e) {
        //    return null;
        //} //finally {
        //manager.close();
        //}

    }

    @Transactional
    @Override
    public User remove(int id) {

        //EntityManager manager = factory.createEntityManager();
        //EntityTransaction trans = manager.getTransaction();

        //try {
        User user = manager.find(User.class, id);
        if (user != null) {
            //trans.begin();
            manager.remove(user);
            //trans.commit();
        }
        return user;
        //} catch (Exception e) {
        //trans.rollback();
        //return null;
        //} finally {
        //  manager.close();
        //}
    }

    @Transactional
    @Override
    public User update(User entity) {

        //EntityManager manager = factory.createEntityManager();
        //EntityTransaction trans = manager.getTransaction();

        //try {
        //trans.begin();
        if (entity.getId() == 0) {
            manager.persist(entity);
        } else {
            entity = manager.merge(entity);
        }
        // trans.commit();
        return entity;
        //} catch (Exception e) {
        //trans.rollback();
        //return null;
        //} finally {
        //    manager.close();
        // }
    }

    //public EntityManagerFactory getFactory() {return factory;}

    //public void setFactory(EntityManagerFactory factory) {this.factory = factory;}

}
