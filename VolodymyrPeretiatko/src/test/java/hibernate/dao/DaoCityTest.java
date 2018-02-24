package hibernate.dao;

import hibernate.model.Base;
import hibernate.model.City;
import org.junit.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoCityTest {

    private static EntityManagerFactory factory;
    private static Dao dao;


    //Test entity's
    private static Base cityKiev = new City("Kiev");
    private static Base cityLviv = new City("Lviv");
    private static Base cityTest = new City("Test");

    @BeforeClass
    public static void init() throws Exception {
        factory = Persistence.createEntityManagerFactory("hibernate-unit");
        dao = new DaoCityImpl(factory);

        dao.create(cityKiev);
        dao.create(cityLviv);

    }

    @AfterClass
    public static void down() throws Exception {
        factory.close();
        dao = null;
    }

    @Test
    public void create() throws Exception {

        Base newEntity = (Base) dao.create(cityTest);
        Assert.assertTrue(newEntity.getId() != 0);
    }

    @Test
    public void findAll() throws Exception {
        Assert.assertTrue(dao.findAll().size() == 2);
    }

    @Test
    public void findAllparams() throws Exception {
        Assert.assertTrue(dao.findAll(0,1).size() == 1);
    }

    @Test
    public void find() throws Exception {
        Assert.assertTrue(((Base)dao.find(1)).getId() == 1);
    }

    @Test
    public void remove() throws Exception {
        dao.remove(2);
        Assert.assertTrue(dao.findAll().size() == 2);
    }

    @Test
    public void update() throws Exception {

        Base entity = (Base) dao.create(cityKiev);
        entity.setName("Updated");
        dao.update(entity);
        Base foundEntity = (Base) dao.find(1);

        Assert.assertTrue("Updated".equals(entity.getName()));
        Assert.assertTrue(dao.findAll().contains(foundEntity));

    }

}