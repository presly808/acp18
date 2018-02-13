package hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table(name = "cities")
public class City extends Base {
    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public City(String name) {
        super(name);
    }


}
