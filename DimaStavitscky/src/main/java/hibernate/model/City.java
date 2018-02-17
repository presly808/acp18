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

    public City(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        City city = (City) o;

        return super.id == city.getId();
    }


}
