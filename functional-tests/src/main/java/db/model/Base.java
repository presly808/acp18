package db.model;

/**
 * Created by serhii on 03.02.18.
 */
public class Base {

    protected int id;
    protected String name;

    public Base() {
    }

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
