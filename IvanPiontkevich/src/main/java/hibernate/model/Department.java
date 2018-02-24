package hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table(name = "departments")
public class Department extends Base {

    public Department() {
    }

    public Department(String name) {
        super(name);
    }

    public Department(int id, String name) {
        super(id, name);
    }
}
