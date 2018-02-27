package hibernate.dao;

import hibernate.model.Department;

import javax.persistence.EntityManagerFactory;

public class DepartmentDao extends DaoImpl<Department, Integer> {

    public DepartmentDao(EntityManagerFactory factory, Class<Department> clazz)
    {
        super(factory, clazz);
    }

}
