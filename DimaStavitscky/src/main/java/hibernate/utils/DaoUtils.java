package hibernate.utils;

import hibernate.model.Base;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;

public class DaoUtils<T> {

    public static Base getBaseObject(Object obj){
        Class clas = obj.getClass();
        if(clas.getSuperclass() != Base.class) {
            throw new ExceptionInInitializerError("object does not inherit from Base");

        } else if(clas == User.class){
            return (User) obj;

        } else if(clas == Department.class){
            return (Department) obj;

        } else if(clas == City.class) {
            return (City) obj;
        }

        return null;

    }
}
