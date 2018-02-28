package hibernate.dao;

import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.User;

import java.util.List;
import java.util.Map;

public interface CityDao extends Dao<City, Integer> {

    Map<City, List<User>> getUsersGroupByCity() throws AppException;
}
