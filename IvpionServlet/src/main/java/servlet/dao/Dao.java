package servlet.dao;


import java.util.List;

public interface Dao<T> {

    T find(int id);
    List<T> findAll();
    T save(T obj);
    T delete(int id);
    T findByEmail(String email);

}
