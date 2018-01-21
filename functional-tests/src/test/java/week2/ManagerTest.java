package week2;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by serhii on 19.01.18.
 */
public class ManagerTest {
    @Test
    public void calculateSalary() throws Exception {
        Manager manager = new Manager("Oleg", 3000);
        manager.addSubworker(new Employee("Ivan", 2000));
        manager.addSubworker(new Employee("Valera", 2000));
        manager.addSubworker(new Employee("Olena", 2000));

        Assert.assertThat(manager.calculateSalary(), CoreMatchers.equalTo(3300));

    }

    @Test
    public void addSubworker() throws Exception {
        Manager manager = new Manager("Oleg", 3000);
        Assert.assertThat(manager.addSubworker(new Employee("Ivan", 2000)),
                CoreMatchers.equalTo(true));
    }

    @Test
    public void getSubworkers() throws Exception {
        Manager manager = new Manager("Oleg", 3000);
        manager.addSubworker(new Employee("Ivan", 2000));
        manager.addSubworker(new Employee("Valera", 2000));
        manager.addSubworker(new Employee("Petro", 2000));

        Assert.assertThat(manager.getSubworkers().size(), CoreMatchers.equalTo(3));
    }

}