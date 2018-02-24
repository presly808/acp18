package hibernate.dao;

import hibernate.model.City;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DaoCityImpl implements DaoCity{

    private EntityManagerFactory factory;

    public DaoCityImpl(EntityManagerFactory factory) {
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

    @Override
    public boolean removeAll() {
        return DaoUtilH2Db.removeAll(City.class, factory);
    }

    @Override
    public City getByName(String name) {
        return (City) DaoUtilH2Db.findByName(City.class, name, factory);
    }
}
