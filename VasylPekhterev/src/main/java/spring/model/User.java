package spring.model;

import javax.persistence.*;

/**
 * Created by serhii on 17.02.18.
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    public User() {
    }

    public User(String name) {
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
}
