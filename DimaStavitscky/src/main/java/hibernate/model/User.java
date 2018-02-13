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

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manage;

    // start work date
    @Column
    private LocalDateTime localDateTime;

    public User() {
    }

    public User(int id, String name, int age) {
        super(id, name);
        this.age = age;
    }

    public User(int age, double salary, Department department, City city) {
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        manage = null;
    }

    public User(int id, String name, int age, double salary, Department department, City city) {
        super(id, name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
    }

    public User(String name, int age, double salary, Department department, City city, User manage) {
        super.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.manage = manage;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", salary=" + salary +
                ", department=" + department +
                ", city=" + city +
                ", manage=" + manage +
                ", localDateTime=" + localDateTime +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public User getManage() {
        return manage;
    }

    public void setManage(User manage) {
        this.manage = manage;
    }
}
