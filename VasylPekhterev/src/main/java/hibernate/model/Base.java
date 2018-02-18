package hibernate.model;

import javax.persistence.*;

/**
 * Created by serhii on 03.02.18.
 */
@NamedQueries({
        @NamedQuery(name="selectFromCity", query="from City"),
        @NamedQuery(name="selectFromDepartment", query="from Department"),
        @NamedQuery(name="selectFromUser", query="from User"),
        @NamedQuery(name="findByNameCity", query="from City c where c.name = :name"),
        @NamedQuery(name="findByNameDepartment", query="from Department d where d.name = :name"),
        @NamedQuery(name="findByNameUser", query="from User u where u.name = :name"),
        @NamedQuery(name="findBySalaryUser", query="from User u where u.salary BETWEEN :minSalary AND :maxSalary"),
        @NamedQuery(name="findByDateUser", query="from User u where u.localDateTime BETWEEN :sDate AND :eDate"),
        @NamedQuery(name="avgSalaryGroupByDepartmentUser", query="SELECT u.department," +
                " AVG(u.salary) AS salary from User u GROUP BY u.department"),
        /*String.format("SELECT %s, %s(%s) AS %s FROM %s GROUP BY %s",
                groupField.getName(),
                agrFunc,
                agrField.getName(),
                agrField.getName(),
                clazz.getSimpleName(),
                groupField.getName());*/
})
@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Base {

    @Id
    @GeneratedValue
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
}
