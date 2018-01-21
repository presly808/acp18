package company.model;

import java.util.List;

/**
 * You can add any additional classes to make structure more flexible and scalable
 */
public class Manager extends Employee {

    public Manager(String name, int salary) {
        super(name,salary);

    }

    public boolean addSubworker(Employee employee){
        return false;
    }

    public List<Employee> getSubworkers(){
        return null;
    }


}
