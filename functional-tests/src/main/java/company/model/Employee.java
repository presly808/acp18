package company.model;


import company.utils.MyUtils;

import java.util.Date;
import java.util.GregorianCalendar;

public class Employee {

    private int id;
    private String name;
    private int salary;
    private GregorianCalendar birthday;
    private Date startWorkDate;
    private Date endWorkDate;

    public Employee(String name, int salary) {
        this.id = MyUtils.genId();
    }

    public Employee(String name, int salary, GregorianCalendar calendar, Double startWorkDate) {
        this.id = MyUtils.genId();
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public void setBirthday(GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    public Date getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(Date startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public int getId() {
        return id;
    }

    public Date getEndWorkDate() {
        return endWorkDate;
    }

    public void setEndWorkDate(Date endWorkDate) {
        this.endWorkDate = endWorkDate;
    }
}
