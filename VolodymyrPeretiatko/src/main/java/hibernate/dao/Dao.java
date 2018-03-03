package hibernate.dao;

import java.util.List;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T, K> {

    T create(T entity);
    List<T> findAll();
    List<T> findAll(int offset, int length);
    T find(K id);
    T remove(K id);
    T update(T entity);
    boolean removeAll();
}
