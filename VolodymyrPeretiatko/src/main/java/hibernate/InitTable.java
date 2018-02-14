package hibernate;


import hibernate.dao.DaoCity;
import hibernate.model.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class InitTable {
    public static void main(String[] args) {
        //~/IdeaProjects/acp18/VolodymyrPeretiatko/src/main/resources/

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-unit");
        //EntityManager entityMng =  entityManagerFactory.createEntityManager();



        //entityMng.close();

        DaoCity dc = new DaoCity(entityManagerFactory);

        City city = dc.create(new City("Kiev"));
        System.out.println(city.getId());

        City c2 = dc.find(1);

        System.out.println(c2.getName());

        City c = new City("Dno");
        c.setId(1);

        dc.update(c);

        c2 = dc.find(1);

        System.out.println(c2.getName());

        List<City> list = dc.findAll();

        System.out.println(list.toString());

        dc.remove(city.getId());

        entityManagerFactory.close();
    }
}
