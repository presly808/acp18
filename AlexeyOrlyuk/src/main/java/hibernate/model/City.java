package hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * City POJO model.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see Base
 */
@Entity
@Table(name = "cities")
public class City extends Base {

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public City(int id, String name) {
        super(id, name);
    }
}
