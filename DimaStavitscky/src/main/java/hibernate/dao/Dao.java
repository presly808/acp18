package hibernate.dao;

import java.util.List;

/**
 * Created by serhii on 10.02.18.
 */
public interface Dao<T, I> {

    T create(T entity);

    List<T> findAll();

    List<T> findAll(int offset, int length);

    T find(I id);

    T remove(I id);

    T update(T entity);

    I deleteTable();
}
