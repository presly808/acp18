package hibernate.utils;

import hibernate.exception.exclude.AppException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CrudOperations {
    private static final Logger LOGGER = Logger.getLogger(CrudOperations.class);

    public static <T> T create(T entity, EntityManager manager) {
        manager.persist(entity);
        return entity;
    }

    public static <T> List<T> findAll(Class<T> clazz, EntityManager manager) {
        try {
            TypedQuery<T> query = manager.createQuery
                    ("FROM " + clazz.getSimpleName(), clazz);
            return query.getResultList();

        } catch (NoResultException e) {
            LOGGER.error(String.format("There are no %ss in the database", clazz.getSimpleName()));
            throw e;
        }
    }

    public static <T> List<T> findAll(int offset, int length, Class<T> clazz, EntityManager manager) {
        try {
            TypedQuery<T> query = manager.createQuery
                    ("FROM " + clazz.getSimpleName(), clazz);
            query.setFirstResult(offset);
            query.setMaxResults(length);

            return query.getResultList();

        } catch (NoResultException e) {
            LOGGER.error(String.format("There are no %ss in the database", clazz.getSimpleName()));
            throw e;
        }
    }

    public static <T> T find(int id, Class<T> clazz, EntityManager manager) {
        T foundEntity = manager.find(clazz, id);
        if (foundEntity == null) {
            LOGGER.error(String.format("Can't find the %s, it is not in the database, id: %d",
                    clazz.getSimpleName(), id));
        }

        return foundEntity;
    }

    public static <T> T findByName(String name, Class<T> clazz, EntityManager manager) {
        TypedQuery<T> query = manager.createQuery(String.format(
                "FROM %s e WHERE e.name = :name", clazz.getSimpleName()), clazz);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();

        } catch (NoResultException e) {
            LOGGER.error(String.format("Can't find the %s, it is not in the database, name: %s",
                    clazz.getSimpleName(), name));
            throw e;
        }
    }

    public static <T> T remove(int id, Class<T> clazz, EntityManager manager) {
        T removedEntity = manager.find(clazz, id);

        if (removedEntity == null) {
            LOGGER.error(String.format("Can't delete a %s, it is not in the database, id: %d",
                    clazz.getSimpleName(), id));
            return null;
        }

        manager.remove(id);
        return removedEntity;
    }

    public static <T> T update(T entity, int id, EntityManager manager) {
        if (manager.find(entity.getClass(), id) == null) {
            LOGGER.error(String.format("Can't update the %s, it is not in the database",
                    entity.getClass().getSimpleName()));

        } else {
            manager.merge(entity);
        }

        return entity;
    }
}