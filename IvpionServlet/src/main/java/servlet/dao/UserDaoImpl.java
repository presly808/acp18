package servlet.dao;

import org.springframework.stereotype.Component;
import servlet.model.ServUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component(value = "userDaoDean")
public class UserDaoImpl implements Dao<ServUser> {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public ServUser find(int id) {
        return manager.find(ServUser.class, id);
    }

    @Override
    public List<ServUser> findAll() {
        return manager.createQuery("select u from ServUser u", ServUser.class).getResultList();
    }

    @Override
    public ServUser save(ServUser obj) {
        manager.persist(obj);
        return obj;
    }

    @Override
    public ServUser delete(int id) {
        ServUser find = manager.find(ServUser.class, id);
        manager.remove(find);
        return find;
    }

    @Override
    public ServUser findByEmail(String email) {
       return manager.createQuery("select u from ServUser u where u.email = :email", ServUser.class).setParameter("email", email).getSingleResult();
    }

}
