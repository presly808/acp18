package hibernate;

import hibernate.dao.DaoImpl;
import hibernate.entetyManagerSingle.EntetyManagerSingleton;
import hibernate.exception.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.service.MainService;
import hibernate.service.MainServiceImpl;
import org.junit.BeforeClass;

import java.time.LocalDateTime;

public class HiberDBTest {

    @BeforeClass
    public static void initDB(){
        EntetyManagerSingleton.getEntetyManagerSingleton();
        MainService mainService = new MainServiceImpl(
                new DaoImpl<>(City.class,EntetyManagerSingleton.getEntityManager()),
                new DaoImpl<>(Department.class,EntetyManagerSingleton.getEntityManager()),
                new DaoImpl<>(User.class,EntetyManagerSingleton.getEntityManager()));

        try {
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

            User user = new User("Nazar", 34, 2000, department1, city3, null, LocalDateTime.now());
            mainService.register(user);
            User user2 = new User("Kolya", 35, 3000.00, department2, city1, user, LocalDateTime.now());
            User user3 = new User("Olya", 51, 2500.00, department1, city1, user2, LocalDateTime.now());
            User user4 = new User("Andy", 40, 2200.00, department2, city2, user, LocalDateTime.now());
            User user5 = new User("Roma", 33, 1500.00, department1, city2, user2, LocalDateTime.now());
            User user6 = new User("Igor", 42, 2800.00, department2, city1, user2, LocalDateTime.now());
            User user7 = new User("Andy", 22, 1000.00, department2, city3, user, LocalDateTime.now());

            mainService.register(user2);
            mainService.register(user3);
            mainService.register(user4);
            mainService.register(user5);
            mainService.register(user6);
            mainService.register(user7);

        } catch (AppException e) {
            e.printStackTrace();
        }



    }
}