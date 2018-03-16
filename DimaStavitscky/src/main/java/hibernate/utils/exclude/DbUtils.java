package hibernate.utils.exclude;

import hibernate.exception.exclude.AppException;
import hibernate.model.City;
import hibernate.model.Department;
import hibernate.model.User;
import hibernate.service.MainService;

import java.time.LocalDateTime;

public class DbUtils {

    public static void addTestInfoToDB(MainService service) throws AppException {
        System.out.println("Test DB start creating");
        if (service.getAllUsers().size() > 0) {
            System.out.println("Test DB already created");

        } else {
            City kiev = new City("Kiev");
            City odessa = new City("Oddessa");

            Department department1 = new Department("IT");
            Department department2 = new Department("QA");

            LocalDateTime date2000 = LocalDateTime.of(2000, 1, 1, 1, 1);
            LocalDateTime date2005 = LocalDateTime.of(2005, 1, 1, 1, 1);
            LocalDateTime date2010 = LocalDateTime.of(2010, 1, 1, 1, 1);
            LocalDateTime date2015 = LocalDateTime.of(2015, 1, 1, 1, 1);

            User user1 = new User("Ivan", 30, 2500, "I", "1", department1, kiev, null, date2000);
            User user2 = new User("Oleg", 33, 3500, "O", "2", department2, odessa, user1, date2005);
            User user3 = new User("Yura", 35, 1500, "Y", "3", department1, kiev, user2, date2010);
            User user4 = new User("Serhii", 22, 2500, "S", "4", department2, odessa, user1, date2015);
            User user5 = new User("Olex", 24, 4500, "Ol", "5", department1, kiev, user2, date2005);

            service.addCity(kiev);
            service.addCity(odessa);

            service.addDepartment(department1);
            service.addDepartment(department2);

            service.register(user1);
            service.register(user2);
            service.register(user3);
            service.register(user4);
            service.register(user5);



            System.out.println("TEST DB WAS CREATED");
        }
    }
}
