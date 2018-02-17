package hibernate.dao;

import hibernate.exception.AppException;
import hibernate.model.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class DaoUserImpl implements DaoUser {

    private EntityManagerFactory factory;
    private static final Logger LOG = Logger.getLogger(DaoUserImpl.class);

    public DaoUserImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public User create(User entity) {
        return (User) DaoUtilH2Db.create(entity, factory);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) DaoUtilH2Db.findAll(User.class, factory);
    }

    @Override
    public List<User> findAll(int offset, int length) {
        return (List<User>) DaoUtilH2Db.findAll(User.class, factory, offset, length);
    }

    @Override
    public User find(Integer id) {
        return (User) DaoUtilH2Db.find(User.class, id, factory);
    }

    @Override
    public User remove(Integer id) {
        return (User) DaoUtilH2Db.remove(User.class, id, factory);
    }

    @Override
    public User update(User entity) {
        return (User) DaoUtilH2Db.update(User.class, entity, factory);
    }

    @Override
    public boolean removeAll() {
        return DaoUtilH2Db.removeAll(User.class, factory);
    }

    //DaoUsr

    @Override
    public List getAvgSalaryGroupByDepartment() throws AppException {

        LOG.info("getAvgSalaryGroupByDepartment UserDaoImpl");

        String queryTxt = "SELECT e.department, AVG(e.salary) " +
                          "FROM User e " +
                          "GROUP BY e.department";

        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery(queryTxt);

        return query.getResultList();

    }

    @Override
    public List getUsersByCityAndOrdered(String city) throws AppException {

        LOG.info("getUsersByCityAndOrdered UserDaoImpl");

        String queryTxt = "SELECT e " +
                          "FROM User e " +
                          "WHERE e.city.name = :city " +
                          "ORDER BY e.name";

        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery(queryTxt);
        query.setParameter("city", city);

        return query.getResultList();

    }

    @Override
    public List<User> findByName(String name) throws AppException {

        LOG.info("findByName UserDaoImpl");

        String queryTxt = "SELECT e " +
                          "FROM User e " +
                          "WHERE e.name = :name";

        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery(queryTxt);
        query.setParameter("name", name);

        return query.getResultList();
    }

    @Override
    public List<User> findInSalaryRange(double minSal, double maxSal) throws AppException {
        LOG.info("findByName UserDaoImpl");

        String queryTxt = "SELECT e " +
                          "FROM User e " +
                          "WHERE e.salary >= :minSal AND e.salary <= :maxSal";

        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery(queryTxt);
        query.setParameter("minSal", minSal);
        query.setParameter("maxSal", maxSal);

        return query.getResultList();
    }

    @Override
    public List<User> findByDateRange(LocalDateTime start, LocalDateTime end) throws AppException {
        LOG.info("findByName UserDaoImpl");

        String queryTxt = "SELECT e " +
                          "FROM User e " +
                          "WHERE e.localDateTime >= :start AND e.localDateTime <= :end";

        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery(queryTxt);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();
    }
}
