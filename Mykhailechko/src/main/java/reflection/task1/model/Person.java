package reflection.task1.model;

import reflection.task1.MyField;

public class Person {

    @MyField
    public String name;
    @MyField
    public int age;
    public int salary;
    public String nickname;

    public Person(String name, int age, int salary, String nickname) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.nickname = nickname;
    }
    public Person() {
    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
