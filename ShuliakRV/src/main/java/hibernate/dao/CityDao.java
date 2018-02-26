package hibernate.dao;

import hibernate.model.City;

import javax.persistence.EntityManagerFactory;

public class CityDao extends DaoImpl<City,Integer> {

    public CityDao(EntityManagerFactory factory, Class<City> cityClass) {
        super(factory,cityClass);
    }
}
