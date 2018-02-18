package hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table
public class Department extends Base implements Serializable {

    @OneToMany(mappedBy="department")
    private Set<User> users;

    public Department() {
    }

    public Department(int id, String name) {
        super(id, name);
    }

    public Department(String name) {
        super(name);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}



