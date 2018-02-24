package hibernate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serhii on 03.02.18.
 */

@Entity
@Table
public class Department extends Base {

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Department() {
    }

    public Department(int id, String name) {
        super(id, name);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Department(String name) {
        super(name);
    }

}
