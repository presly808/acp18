package spring.model;

import javax.persistence.*;

/**
 * Created by serhii on 17.02.18.
 */

@Entity
@Table(name = "users_spring")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    @Column
    public String name;
    @Column
    public String password;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password) {

        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
