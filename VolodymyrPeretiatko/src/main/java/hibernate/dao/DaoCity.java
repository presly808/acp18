package hibernate.dao;

import hibernate.model.City;
import org.apache.log4j.Logger;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DaoCity implements Dao<City, Integer>{

    private EntityManagerFactory factory;

    private static final Logger LOG = Logger.getLogger(DaoCity.class);

    public DaoCity(EntityManagerFactory factory) {
        this.factory = factory;
    }


    public City create(City entity){
        return (City) DaoUtilH2Db.create(entity, factory, LOG);
    }

    @Override
    public List<City> findAll() {
        return null;
    }

    @Override
    public List<City> findAll(int offset, int length) {
        return null;
    }

    @Override
    public City find(Integer integer) {
        return null;
    }

    @Override
    public City remove(Integer id) {
        return (City) DaoUtilH2Db.remove(City.class, id, factory, LOG);
    }

    @Override
    public City update(City entity) {
        return null;
    }
}
