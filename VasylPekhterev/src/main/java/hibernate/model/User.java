package hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table
public class User extends Base implements Serializable {

    @Column
    private int age;

    @Column
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "city_id",referencedColumnName = "id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manage;

    @Column// start work date
    private LocalDateTime localDateTime;

    @OneToMany(mappedBy="manage")
    private Set<User> users;

    public User() {
    }

    public User(int age, double salary, Department department, City city) {
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
    }

    public User(int id) {
        this.id = id;
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

    public User(String name, int age, double salary, Department department, City city) {
        super(name);
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
        this.manage = manage;
    }

    public User(String name, int age, double salary, Department department, City city, User manage) {
        super(name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.manage = manage;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
