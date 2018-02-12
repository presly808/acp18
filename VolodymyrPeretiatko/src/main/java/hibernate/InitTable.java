package hibernate;


import hibernate.dao.DaoCity;
import hibernate.model.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InitTable {
    public static void main(String[] args) {
        //~/IdeaProjects/acp18/VolodymyrPeretiatko/src/main/resources/

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-unit");
        EntityManager entityMng =  entityManagerFactory.createEntityManager();



        entityMng.close();

        DaoCity dc = new DaoCity(entityManagerFactory);

        dc.create(new City("Kiev"));

        entityManagerFactory.close();
    }
}
