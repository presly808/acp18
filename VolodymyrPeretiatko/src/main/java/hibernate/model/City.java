package hibernate.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by serhii on 03.02.18.
 */
@Entity
@Table(name = "Cities", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class City extends Base {

}
