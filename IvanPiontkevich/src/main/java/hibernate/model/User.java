package hibernate.model;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Created by serhii on 03.02.18.
 */

@Entity
@Table(name = "users")
public class User extends Base {
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User manage;
    // start work date

    @Column(name = "start_work")
    private LocalDateTime localDateTime;

    public User() {
    }


    public User(String name, int age) {
        super(name);
        this.age = age;
    }


    public User(String name, int age, double salary, Department department, City city, User manager) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.localDateTime = LocalDateTime.now();
        this.manage = manager;
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
        this.manage = manage;
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
                ", manage=" + manage +
                ", localDateTime=" + localDateTime +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public User clone() {
        return new User(id, name, age, salary, department, city, manage);
    }
}
