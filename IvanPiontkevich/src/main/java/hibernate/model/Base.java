package hibernate.model;


import javax.persistence.*;

/**
 * Created by serhii on 03.02.18.
 */

@MappedSuperclass
public abstract class Base {
    @Id
    @GeneratedValue
    protected int id;
    @Column(name = "name", nullable = false)
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
