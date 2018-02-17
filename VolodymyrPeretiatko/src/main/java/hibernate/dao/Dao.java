package hibernate.dao;

import java.util.List;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T, Id> {

    T create(T entity);
    List<T> findAll();
    List<T> findAll(int offset, int length);
    T find(Id id);
    T remove(Id id);
    T update(T entity);
    boolean removeAll();
}
