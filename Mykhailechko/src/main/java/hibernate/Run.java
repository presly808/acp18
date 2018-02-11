package hibernate;

import hibernate.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Run {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hiberdb");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(new Person("Nazar", 25));
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
