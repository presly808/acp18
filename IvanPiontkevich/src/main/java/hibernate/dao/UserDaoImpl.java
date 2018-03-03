package hibernate.dao;

import hibernate.model.User;
import hibernate.wrap.TransactionWrapper;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private EntityManagerFactory factory;


    public UserDaoImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        LOGGER.info("start find all Users");
        return TransactionWrapper.wrap(factory, new User(), -1).execute(((manager, entity, limit) ->
                manager.createQuery("select u from User u", User.class)
                        .setMaxResults(limit)
                        .getResultList()));
    }

    @Override
    public List<User> findAll(int offset, int length) {
        LOGGER.info("start find users with limit");
        return TransactionWrapper.wrap(factory, new User(), length).execute((manager, entity, limit) ->
                manager.createQuery("select u from User u", User.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

    @Override
    public User find(Integer integer) {
        return TransactionWrapper.wrap(factory, new User()).execute((manager, entity) -> {
            return manager.find(entity.getClass(), integer);
        });
    }

    @Override
    public User remove(Integer integer) {
        return TransactionWrapper.wrap(factory, new User()).executeWithTransaction((manager, entity) -> {
                    User deleted = manager.find(entity.getClass(), integer);
                    manager.remove(deleted);
                    return deleted;
                }
        );
    }

    @Override
    public User create(User obj) {
        LOGGER.info("create and save new User");
        TransactionWrapper.wrap(factory, obj).executeWithTransaction(EntityManager::persist);

        LOGGER.info("new User was successfully created");
        return TransactionWrapper.wrap(factory, obj)
                .execute((manager, wrapEntity) -> {
                    return manager.find(wrapEntity.getClass(), wrapEntity.getId());
                });
    }


    @Override
    public User update(User entity) {
        return TransactionWrapper.wrap(factory, entity).executeWithTransaction((manager, entity1) -> {
            User fromDB = manager.find(entity1.getClass(), entity1.getId());
            //todo how do that more simple
            if (entity.getCity() != null && entity.getCity().getId() == 0){
                manager.persist(entity.getCity());
            }
            if (entity.getDepartment() != null && entity.getDepartment().getId() == 0) {
                manager.persist(entity.getDepartment());
            }
            if (entity.getManage() != null && entity.getManage().getId() == 0){
                manager.persist(entity.getManage());
            }
            manager.merge(entity1);
            return fromDB;
        });
    }


}

