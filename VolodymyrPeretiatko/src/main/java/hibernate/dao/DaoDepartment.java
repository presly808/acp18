package hibernate.dao;

import hibernate.model.Department;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class DaoDepartment implements Dao<Department, Integer> {

    private EntityManagerFactory factory;

    public DaoDepartment(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Department create(Department entity){
        return (Department) DaoUtilH2Db.create(entity, factory);
    }

    @Override
    public List<Department> findAll() {
        return (List<Department>) DaoUtilH2Db.findAll(Department.class, factory);
    }

    @Override
    public List<Department> findAll(int offset, int length) {
        return (List<Department>) DaoUtilH2Db.findAll(Department.class, factory, offset, length);
    }

    @Override
    public Department find(Integer id) {
        return (Department) DaoUtilH2Db.find(Department.class, id, factory);
    }

    @Override
    public Department remove(Integer id) {
        return (Department) DaoUtilH2Db.remove(Department.class, id, factory);
    }

    @Override
    public Department update(Department entity) {
        return (Department) DaoUtilH2Db.update(Department.class, entity, factory);
    }
}
