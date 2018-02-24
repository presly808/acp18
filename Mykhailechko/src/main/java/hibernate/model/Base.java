package hibernate.model;

import javax.persistence.*;

/**
 * Created by serhii on 03.02.18.
 */

@MappedSuperclass
@NamedQueries({@NamedQuery(name = "getUsersGroupByDepartment", query = "SELECT u from User u"),
               @NamedQuery(name = "getAvgSalaryGroupByDepartment", query = "SELECT u.department, avg(u.salary) as sal from User u group by u.department"),
               @NamedQuery(name = "getUsersGroupByManagersAndOrderedThatLiveInKiev", query = "SELECT u from User u where u.city.name = 'Kyiv'")
            })

public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    @Column
    protected String name;

    public Base() {
    }

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Base(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base base = (Base) o;

        return id == base.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Base{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
