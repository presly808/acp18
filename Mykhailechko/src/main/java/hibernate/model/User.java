package hibernate.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table
public class User extends Base {

    private int age;
    private double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manage_id", referencedColumnName = "id")
    private User manage;

    // start work date
    @Column(name = "entrydate")
    private LocalDateTime localDateTime;

    public User() {
    }
    public User(String name, int age, double salary, Department department, City city, LocalDateTime localDateTime) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.localDateTime = localDateTime;
    }
    public User(String name, int age, double salary, Department department, City city) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
    }
    public User(String name, int age, double salary, Department department) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.department = department;
    }
    public User(int age, double salary, Department department, City city) {
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
    }
    public User(String name, int age) {
        super(name);
        this.age = age;
    }
    public User(String name, int age, double salary, Department department, City city, User manage, LocalDateTime localDateTime) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.manage = manage;
        this.localDateTime = localDateTime;
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

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", salary=" + salary +
                ", department=" + department +
                ", city=" + city +
                ", localDateTime=" + localDateTime +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
