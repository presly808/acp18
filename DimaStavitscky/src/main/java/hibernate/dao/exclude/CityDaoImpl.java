package hibernate.dao.exclude;

import hibernate.dao.CityDao;
import hibernate.dao.Dao;
import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.User;
import hibernate.utils.exclude.CrudOperationsUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

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

    @Override
    public City create(City entity) {
        int id = entity.getId();
        if(id == 0 || manager.find(entity.getClass(), id) == null) {
            CrudOperationsUtils.create(entity, manager);

        } else {
            LOGGER.error("It is impossible to create a city, " +
                    "the city with this id already exists in the database, id: " + id);
        }
        return entity;
    }

    @Override
    public List<City> findAll() {
        return CrudOperationsUtils.findAll(City.class, manager);
    }

    @Override
    public List<City> findAll(int offset, int length) {
        return CrudOperationsUtils.findAll(offset, length, City.class, manager);
    }

    @Override
    public City find(Integer integer) {
        return CrudOperationsUtils.find(integer, City.class, manager);
    }

    @Override
    public City findByName(String name) {
        return CrudOperationsUtils.findByName(name, City.class, manager);
    }

    @Override
    public City remove(Integer integer) {
        return CrudOperationsUtils.remove(integer, City.class, manager);
    }

    @Override
    public City update(City entity) {
        return CrudOperationsUtils.update(entity, entity.getId(), manager);
    }

    @Override
    public Integer deleteTable() {
        return manager.createQuery("DELETE FROM City").executeUpdate();
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
        return resMap;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}