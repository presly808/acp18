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
    @Column(unique = true)
    private String login;
    @Column(unique = true)
    private String password;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private User manage;

    // start work date
    @Column
    private LocalDateTime localDateTime;

    public User() {
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

    public User(int id, String name, int age, double salary, Department department, City city, User manage, LocalDateTime localDateTime) {
        super(id, name);
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.city = city;
        this.manage = manage;
        this.localDateTime = localDateTime;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        return super.id == user.getId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + age;
        temp = Double.doubleToLongBits(salary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (manage != null ? manage.hashCode() : 0);
        result = 31 * result + (localDateTime != null ? localDateTime.hashCode() : 0);
        return result;
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
