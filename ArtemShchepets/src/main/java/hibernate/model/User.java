package hibernate.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by serhii on 03.02.18.
 */

@Entity
@Table(name = "users")
public class User extends Base {

    @Column
    private int age;

    @Column
    private double salary;

    @Column
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Column
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;
    // start work date

    @Column
    private LocalDateTime localDateTime;

    public User() {
    }

    public User(int age, double salary, Department department, City city) {
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
    }

    public User(int id, String name, int age) {
        super(id, name);
        this.age = age;
    }

    public User(int id, String name, int age, double salary, Department department, City city) {
        super(id, name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User(int id, String name, int age, double salary, Department department, City city, User manage) {
        super(id, name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.manager = manage;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public User getManage() {
        return manager;
    }

    public void setManage(User manage) {
        this.manager = manage;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
