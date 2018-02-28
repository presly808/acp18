package hibernate.dao.exclude;

import hibernate.dao.Dao;
import hibernate.dao.DepartmentDao;
import hibernate.exception.exclude.AppException;
import hibernate.model.Department;
import hibernate.model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DepartmentDaoImpl implements DepartmentDao {

    private static final Logger LOGGER = Logger.getLogger(Dao.class);

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public Department create(Department entity) {
        int id = entity.getId();
        if(id == 0 || manager.find(entity.getClass(), id) == null) {
            manager.persist(entity);

        } else {
            LOGGER.error("This department already exists in the database, id:" + id);
        }
        return entity;
    }

    @Transactional
    @Override
    public List<Department> findAll() {
        TypedQuery<Department> query = manager.createQuery
                ("SELECT u FROM Department u", Department.class);

        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Department> findAll(int offset, int length) {
        TypedQuery<Department> query = manager.createQuery("SELECT d FROM Department d", Department.class);
        query.setFirstResult(offset);
        query.setMaxResults(length);

        return query.getResultList();
    }

    @Transactional
    @Override
    public Department find(Integer id) {
        Department res = manager.find(Department.class, id);
        if (res == null) LOGGER.error("This department is not in the database, id: " + id);

        return res;
    }

    @Transactional
    @Override
    public Department remove(Integer id) {
        Department removedUser = manager.find(Department.class, id);
        manager.remove(id);

        return removedUser;
    }

    @Transactional
    @Override
    public Department update(Department entity) {
        return manager.merge(entity);
    }

    @Transactional
    @Override
    public Map<Department, List<User>> getUsersGroupByDepartment() throws AppException {
        Map<Department, List<User>> resMap = new HashMap<>();
        List<Department> departments = findAll();

        for (Department department : departments) {
            TypedQuery<User> query =
                    manager.createQuery("SELECT u FROM User u " +
                            "WHERE u.department = :department", User.class);

            query.setParameter("department", department);

            resMap.put(department, query.getResultList());
        }

        resMap.entrySet().forEach(System.out::println);

        return resMap;
    }
}
