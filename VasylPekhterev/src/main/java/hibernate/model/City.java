package hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by serhii on 03.02.18.
 */

@Entity
@Table
public class City extends Base implements Serializable {

    @OneToMany(mappedBy="city")
    private Set<User> users;

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public City() {
    }

    public City(int id, String name) {
        super(id, name);
    }

    public City(String name) {
        super(name);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
