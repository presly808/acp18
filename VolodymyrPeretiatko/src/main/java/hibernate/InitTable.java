package hibernate;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InitTable {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-unit");
        EntityManager entityMng =  entityManagerFactory.createEntityManager();
        entityMng.close();
        entityManagerFactory.close();
    }
}
