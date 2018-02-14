package hibernate.dao;

import hibernate.model.City;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DaoCity implements Dao<City, Integer>{

    private EntityManagerFactory factory;

    public DaoCity(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public City create(City entity){
        return (City) DaoUtilH2Db.create(entity, factory);
    }

    @Override
    public List<City> findAll() {
        return (List<City>) DaoUtilH2Db.findAll(City.class, factory);
    }

    @Override
    public List<City> findAll(int offset, int length) {
        return (List<City>) DaoUtilH2Db.findAll(City.class, factory, offset, length);
    }

    @Override
    public City find(Integer id) {
        return (City) DaoUtilH2Db.find(City.class, id, factory);
    }

    @Override
    public City remove(Integer id) {
        return (City) DaoUtilH2Db.remove(City.class, id, factory);
    }

    @Override
    public City update(City entity) {
        return (City) DaoUtilH2Db.update(City.class, entity, factory);
    }
}
