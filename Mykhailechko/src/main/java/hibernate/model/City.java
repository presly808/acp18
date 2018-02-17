package hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table
public class City extends Base {

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public City() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public City(int id, String name) {
        super(id, name);
    }

    public City(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
