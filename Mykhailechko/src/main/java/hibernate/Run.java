package hibernate;

import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.service.MainService;
import hibernate.service.MainServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class Run {
    public static void main(String[] args) throws AppException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hiberdb");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        MainService mainService = new MainServiceImpl(entityManager);

        City city1 = new City("Kyiv");
        City city2 = new City("Lviv");
        City city3 = new City("Ivano-Frankivsk");

        Department department1 = new Department("IT");
        Department department2 = new Department("QA");

        mainService.addCity(city1);
        mainService.addCity(city2);
        mainService.addCity(city3);

        mainService.addDepartment(department1);
        mainService.addDepartment(department2);


        User user = new User("Nazar",34,2000,department1, city3, LocalDateTime.now());
        mainService.register(user);

        mainService.register(new User("Nazar",34, 2000.00, department1, city3, user, LocalDateTime.now()));
        mainService.register(new User("Kolya",35, 3000.00, department2, city1, user, LocalDateTime.now()));
        mainService.register(new User("Olya",51, 2500.00, department1, city1, user, LocalDateTime.now()));
        mainService.register(new User("Andy",40, 2200.00, department2, city2, user, LocalDateTime.now()));
        mainService.register(new User("Roma",33, 1500.00, department1, city2, user, LocalDateTime.now()));
        mainService.register(new User("Igor",42, 2800.00, department2, city1, user, LocalDateTime.now()));


        List<User> users =  mainService.findAll();

        for (User u : users) {
            System.out.println(u.toString());
        }

//        List<User> user1 = mainService.findAll();
//
//        for (User u1 : user1) {
//               u1.toString();
//        }

    }
}