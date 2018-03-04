package servlets.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * ID-holder abstract entity.
 * Used to create concrete ID-contained entities.
 *
 * @author alex323glo
 * @version 1.0
 */
@MappedSuperclass
public abstract class IdAutoGeneratedEntity {

    @Id
    @GeneratedValue
    private int id;

    public IdAutoGeneratedEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
