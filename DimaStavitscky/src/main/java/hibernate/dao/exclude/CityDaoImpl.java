package hibernate.dao.exclude;

import hibernate.dao.CityDao;
import hibernate.dao.Dao;
import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CityDaoImpl implements CityDao {
    private static final Logger LOGGER = Logger.getLogger(Dao.class);

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public List<City> findAll() {
        TypedQuery<City> query = manager.createQuery("SELECT c FROM City c", City.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public City create(City entity) {
        int id = entity.getId();
        if(id == 0 || manager.find(entity.getClass(), id) == null) {
            manager.persist(entity);

        } else {
            LOGGER.error("This city already exists in the database, id:" + id);
        }
        return entity;
    }

    @Transactional
    @Override
    public List<City> findAll(int offset, int length) {
        TypedQuery<City> query = manager.createQuery("SELECT c FROM City c", City.class);
        query.setFirstResult(offset);
        query.setMaxResults(length);

        return query.getResultList();
    }

    @Transactional
    @Override
    public City find(Integer id) {
        City res = manager.find(City.class, id);
        if (res == null) LOGGER.error("This city is not in the database, id: " + id);
        return res;
    }

    @Transactional
    @Override
    public City remove(Integer id) {
        City removedUser = manager.find(City.class, id);
        manager.remove(id);
        return removedUser;
    }

    @Transactional
    @Override
    public City update(City entity) {
        return manager.merge(entity);
    }

    @Override
    public Map<City, List<User>> getUsersGroupByCity() throws AppException {
        Map<City, List<User>> resMap = new HashMap<>();
        List<City> departments = findAll();

        for (City city : departments) {
            TypedQuery<User> query =
                    manager.createQuery("SELECT u FROM User u " +
                            "WHERE u.city = :city", User.class);

            query.setParameter("city", city);

            resMap.put(city, query.getResultList());
        }

        resMap.entrySet().forEach(System.out::println);

        return resMap;
    }
}
