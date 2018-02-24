package hibernate.dao;

import hibernate.model.City;

public interface DaoCity extends Dao<City, Integer>{

    City getByName(String name);

}
