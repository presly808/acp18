package hibernate.dao;

import hibernate.model.City;
import hibernate.model.Department;
import hibernate.util.ActionWrapper;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepartmentDaoTest extends DaoTest {

    private static EntityManagerFactory factory;
    private static Dao<Department, Integer> departmentDao;

    private static List<Department> testDepartmentList;

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = Persistence.createEntityManagerFactory(H2_PERSISTENCE_UNIT);
        departmentDao = new DepartmentDao(factory);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        factory.close();
    }

    @Before
    public void setUp() throws Exception {

        // Init records of "departments" table (5 departments):
        testDepartmentList = new ArrayList<>();
        testDepartmentList.add(new Department("department1"));
        testDepartmentList.add(new Department("department2"));
        testDepartmentList.add(new Department("department3"));
        testDepartmentList.add(new Department("department4"));
        testDepartmentList.add(new Department("department5"));

        // Write records to DB (Note: it will update some entity instances' fields in List, such as id!!!):
        ActionWrapper.wrap(factory, new Department()).executeWithTransaction((manager, entity) -> {
            testDepartmentList.forEach(manager::persist);
        });
    }

    @After
    public void tearDown() throws Exception {
        if (!removeAndCheck(factory, Department.class, new Department(), SELECT_ALL_DEPARTMENTS_QUERY)) {
            throw new Exception("One test table (Departments) wasn't cleaned up during tearDown() execution!");
        }
    }

    @Test
    public void findAll() throws Exception {
        List<Department> expected = testDepartmentList;
        List<Department> actual = departmentDao.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void findAll1() throws Exception {
        int testStartPoint = 1;
        int testLimit = 3;

        List<Department> expected = testDepartmentList.subList(testStartPoint, testStartPoint + testLimit);
        List<Department> actual = departmentDao.findAll(testStartPoint, testLimit);

        assertEquals(expected, actual);
    }

    @Test
    public void find() throws Exception {
        int testId = testDepartmentList.get(0).getId();

        Department expected = testDepartmentList.get(0);
        Department actual = departmentDao.find(testId);

        assertEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        int lastElementIndex = testDepartmentList.size() - 1;
        int testId = testDepartmentList.get(lastElementIndex).getId();

        Department expected = testDepartmentList.get(lastElementIndex);
        Department actual = departmentDao.remove(testId);

        List<Department> expectedList = testDepartmentList.subList(0, lastElementIndex);
        List<Department> actualList = ActionWrapper.wrap(factory, new Department(), ActionWrapper.NO_LIMIT)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_DEPARTMENTS_QUERY, Department.class).setMaxResults(limit).getResultList());

        assertEquals(expected, actual);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void update() throws Exception {
        int testId = testDepartmentList.get(0).getId();
        String testNewName = "new_department_name";

        Department expectedOld = testDepartmentList.get(0);
        Department expectedNew = testDepartmentList.get(0).clone();
        expectedNew.setName(testNewName);

        Department actualOld = departmentDao.update(expectedNew);
        Department actualNew = ActionWrapper.wrap(factory, actualOld).execute((manager, entity) -> {
            return manager.find(entity.getClass(), entity.getId());
        });

        assertEquals(expectedOld, actualOld);
        assertEquals(expectedNew, actualNew);
    }

    @Test
    public void create() throws Exception {
        Department newDepartment = departmentDao.create(new Department("new_department"));

        List<Department> expectedList = testDepartmentList;
        expectedList.add(newDepartment);

        List<Department> actualList = ActionWrapper.wrap(factory, new Department(), ActionWrapper.NO_LIMIT)
                .execute((manager, entity, limit) -> manager
                        .createQuery(SELECT_ALL_DEPARTMENTS_QUERY, Department.class).setMaxResults(limit).getResultList());

        assertEquals(expectedList, actualList);
    }

}