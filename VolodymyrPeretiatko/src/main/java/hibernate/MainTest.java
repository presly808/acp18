package hibernate;

import hibernate.model.Base;
import hibernate.model.City;

public class MainTest {
    public static void main(String[] args) {
       // Base b = new Base();
        City c = new City();
        Base bc = new City();

       // System.out.println(b.getClass() == Base.class);
      //  System.out.println(b.getClass());
        System.out.println(c.getClass());
        System.out.println(bc.getClass());
    }
}
