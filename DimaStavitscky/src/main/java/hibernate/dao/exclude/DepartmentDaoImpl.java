package hibernate.dao.exclude;

import hibernate.dao.Dao;
import hibernate.dao.DepartmentDao;
import hibernate.exception.exclude.AppException;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.utils.CrudOperations;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

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

    @Override
    public Department create(Department entity) {
        int id = entity.getId();
        if(id == 0 || manager.find(entity.getClass(), id) == null) {
            CrudOperations.create(entity, manager);

        } else {
            LOGGER.error("It is impossible to create a department, " +
                    "the department with this id already exists in the database, id: " + id);
        }
        return entity;
    }

    @Override
    public List<Department> findAll() {
        return CrudOperations.findAll(Department.class, manager);
    }

    @Override
    public List<Department> findAll(int offset, int length) {
        return CrudOperations.findAll(offset, length, Department.class, manager);
    }

    @Override
    public Department find(Integer integer) {
        return CrudOperations.find(integer, Department.class, manager);
    }

    @Override
    public Department remove(Integer integer) {
        return CrudOperations.remove(integer, Department.class, manager);
    }

    @Override
    public Department update(Department entity) {
        return CrudOperations.update(entity, entity.getId(), manager);
    }

    @Override
    public Integer deleteTable() {
        return manager.createQuery("DELETE FROM Department").executeUpdate();
    }

    @Override
    public Department findByName(String name) {
        return CrudOperations.findByName(name, Department.class, manager);
    }

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
        return resMap;
    }
}
