package company.controller;

import company.db.AppDb;
import company.model.Manager;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import company.model.Employee;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

public class MainControllerTest {


    @Test
    public void areWorkersEqual() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        Employee em1 = mainController.addEmployee(new Employee("Ivan", 3000));
        Employee em2 = mainController.addEmployee(new Employee("Ivan", 3000));

        assertFalse(mainController.areWorkersEqual(em1.getId(),em2.getId()));

    }


    @Test
    public void areWorkersNotEqual() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        Employee em1 = mainController.addEmployee(new Employee("Ivan", 3000));
        Employee em2 = mainController.addEmployee(new Employee("Serhey", 3000));

        assertFalse(mainController.areWorkersEqual(em1.getId(),em2.getId()));

    }


    @Test
    public void addEmployee() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        Employee withId = mainController.addEmployee(new Employee("Ivan", 3000));
        assertThat(withId.getId(), CoreMatchers.not(0));
    }

    @Test
    public void getAllEmployees() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        mainController.addEmployee(new Employee("Ivan", 3000));
        mainController.addEmployee(new Employee("Ivan", 3000));
        assertThat(mainController.getAllEmployees().size(), CoreMatchers.equalTo(2));

    }

    @Test
    public void calculateSalary() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        Employee withId = mainController.addEmployee(new Employee("Ivan", 3000));
        assertThat(mainController.calculateSalary(withId), CoreMatchers.equalTo(3000));
    }

    @Test
    public void getById() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        Employee withId = mainController.addEmployee(new Employee("Ivan", 3000));
        mainController.addEmployee(new Employee("Ivan", 3000));
        assertThat(mainController.getById(withId.getId()), CoreMatchers.equalTo(withId));

    }

    @Test
    public void findWithFilter() throws Exception {

        MainControllerImpl mainController = new MainControllerImpl(new AppDb());
        mainController.addEmployee(new Employee("anton", 3000));
        mainController.addEmployee(new Employee("Andrey", 3000));
        mainController.addEmployee(new Employee("Ivan", 3000));
        assertThat(mainController.findWithFilter("an").size(), CoreMatchers.equalTo(3));

    }

    @Test
    public void calculateSalaries() throws Exception {

        MainControllerImpl mainController = new MainControllerImpl(new AppDb());

        Manager man = new Manager("anton", 5000);
        man.addSubworker(new Employee("1", 1000));
        man.addSubworker(new Employee("2", 1000));

        mainController.addEmployee(man);
        mainController.addEmployee(new Employee("anton", 3000));
        mainController.addEmployee(new Employee("Andrey", 3000));
        mainController.addEmployee(new Employee("Ivan", 3000));
        assertThat(mainController.calculateSalaries(), CoreMatchers.equalTo(9000 + 5100));

    }

    @Test
    public void filterWithPredicate() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());

        Employee emp1 = new Employee("anton", 1000);

        emp1.setBirthday(new GregorianCalendar(1990, 4, 22));
        emp1.setStartWorkDate(new Date());

        Employee emp2 = new Employee("Andrey", 3000);
        emp2.setBirthday(new GregorianCalendar(1985, 4, 22));
        emp2.setStartWorkDate(new Date(new GregorianCalendar(2017, 4, 22).toInstant().toEpochMilli()));

        Employee emp3 = new Employee("Ivan", 4000);
        emp3.setBirthday(new GregorianCalendar(1990, 4, 22));
        emp3.setStartWorkDate(new Date(new GregorianCalendar(2017, 4, 22).toInstant().toEpochMilli()));

        mainController.addEmployee(emp1);
        mainController.addEmployee(emp2);
        mainController.addEmployee(emp3);

        List<Employee> result = mainController.filterWithPredicate((employee -> {
            boolean res = true;

            if (employee.getSalary() < 3000) {
                return false;
            }

            return res;

        }), Comparator.comparing(Employee::getName));


        assertThat(result, CoreMatchers.hasItem(emp2));
        assertThat(result, CoreMatchers.hasItem(emp3));
        assertThat(result.get(0).getName(), CoreMatchers.equalTo("Andrey"));

    }

    @Test
    public void fireWorker() throws Exception {
        MainControllerImpl mainController = new MainControllerImpl(new AppDb());

        Employee emp1 = new Employee("anton", 1000);

        emp1.setBirthday(new GregorianCalendar(1990, 4, 22));
        emp1.setStartWorkDate(new Date());

        mainController.addEmployee(emp1);

        assertThat(mainController.fireWorker(emp1.getId()), CoreMatchers.equalTo(emp1));
    }


    @Test
    public void updateWorker() throws Exception {

    }

}