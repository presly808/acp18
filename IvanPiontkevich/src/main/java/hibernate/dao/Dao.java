package hibernate.dao;

import java.util.List;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T, I> {

    List<T> findAll();
    List<T> findAll(int offset, int length);
    T find(I i);
    T remove(I i);
    T create(T obj);
    T update(T entity);
}
