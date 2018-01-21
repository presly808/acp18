package company.utils.filtering;

import company.model.Employee;

/**
 * Created by serhii on 21.01.18.
 */
public interface EmployeePredicate {

    boolean filter(Employee employee);

}
