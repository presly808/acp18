package hibernate.dao;

import hibernate.exception.AppException;

import java.util.List;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T,ID> {

    List<T> findAll() throws AppException;
    List<T> findAll(int offset, int length);
    T find(ID id);
    T remove(ID id);
    T update(T entity);
}
