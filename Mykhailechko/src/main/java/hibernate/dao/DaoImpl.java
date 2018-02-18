package hibernate.dao;

import hibernate.model.Department;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DaoImpl<T,ID> implements Dao<T,ID> {

    private static final DaoImpl instance = new DaoImpl();
    private static EntityManager entityManager;
    private T persistentClass;

    // todo factory must be passed or manage, we should not create factory inside

    //private DaoImpl() {
    //}

    public static DaoImpl getInstance(EntityManager em){
        //entityManagerFactory = Persistence.createEntityManagerFactory("hiberdb");
        //entityManager = entityManagerFactory.createEntityManager();
         entityManager = em;
         return instance;
    }

    @Override
    public List<T> findAll() {
        List<T> result = entityManager.createQuery("SELECT t FROM " + getPersistentClass().getClass().getSimpleName() + " t").getResultList();

        //List<T> result = entityManager.createQuery("SELECT t FROM T t").getResultList();
        return result;
    }

    @Override
    public List<T> findAll(int offset, int length) {
        //entityManager.createQu
        Query query = entityManager.createQuery("SELECT t FROM " + getPersistentClass().getClass().getSimpleName()
                + " t WHERE ROWNUM() BETWEEN " + offset + " AND " + length);

        List<T> result = query.getResultList();
        return result;
    }

    @Override
    public T find(ID id) {

        T t;
        try {
             t = entityManager.find((Class<T>) getPersistentClass().getClass(), id);
            if (t == null) {
                System.out.println("Client not found!");
                return null;
            }
        }catch (Exception e){
            return null;
        }

        return t;
    }

    @Override
    public T remove(ID id) {

        T t = find(id);
        entityManager.getTransaction().begin();
        try{
            entityManager.remove(t);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }

        Query query = this.entityManager.createQuery("SELECT d FROM Department d", Department.class);
        List<Department> departments = (List<Department>) query.getResultList();
        departments.stream().forEach(System.out::println);

        return t;
    }

    @Override
    public T update(T entity) {

        T mergeEntity;

        entityManager.getTransaction().begin();
        try{
            mergeEntity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }

        return mergeEntity;
    }

    @Override
    public T create(T entity) {

        setPersistentClass(entity);

        entityManager.getTransaction().begin();
        try{
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    public T getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(T persistentClass) {
        this.persistentClass = persistentClass;
    }

}