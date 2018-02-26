package servlets.dao;

import servlets.model.User;

import java.util.List;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T,ID> {

    List<T> findAll();
    List<T> findAll(int offset, int length);
    T find(ID id);

    User findByName(String name);

    T remove(ID id);
    T create(T entity);
}
