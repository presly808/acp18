package hibernate.util;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Wrapper for Persistence operations' logic.
 *
 * @author alex323glo
 * @version 1.0
 */
public class ActionWrapper {

    private static final Logger LOGGER = Logger.getLogger(ActionWrapper.class);

    /**
     * Wraps args (factory and entity) into executable statement.
     * Note: will produce statement, which executes Functions and Procedures!
     *
     * @param factory produces needed EntityManager.
     * @param entity target entity, which will be affected.
     * @param <E> type of affected entity.
     * @return wrapped executable statement.
     *
     * @see WrappedStatement
     */
    public static<E> WrappedStatement<E> wrap(EntityManagerFactory factory, E entity) {
        LOGGER.trace("create new instance of WrappedStatement");

        return new WrappedStatement<>(factory, entity);
    }

    /**
     * Wraps args (factory and entity) into executable statement.
     * Note: will produce statement, which executes GroupFunctions!
     *
     * @param factory produces needed EntityManager.
     * @param entity target entity, which will be affected.
     * @param groupResultLimit max number of elements in result List (after GroupFunction execution).
     *                         Note: enter -1, if result List size must not have limit!
     * @param <E> type of affected entity.
     * @return wrapped executable statement.
     *
     * @see WrappedStatement
     * @see GroupFunction
     */
    public static<E> GroupWrappedStatement<E> wrap(EntityManagerFactory factory, E entity, int groupResultLimit) {
        LOGGER.trace("create new instance of GroupWrappedStatement");

        int limit = groupResultLimit == -1 ? Integer.MAX_VALUE : groupResultLimit;

        return new GroupWrappedStatement<>(factory, entity, limit);
    }

    /**
     * Wrapper of executable logic and its arguments (factory and entity).
     *
     * Includes four different variants of statement execution. Here are usage advices:
     *
     *  1. execute(Function) -  use it, if you want to execute some DATA-READ (non-blocking) logic
     *                          with Persistence and get some result of execution.
     *
     *  2. execute(Procedure) - use it, if you want to execute some DATA-READ (non-blocking) logic
     *                          with Persistence and don't want to get any result of execution.
     *
     *  3. executeWithTransaction(Function) -   use it, if you want to execute some DATA-WRITE (blocking) logic
     *                                          with Persistence and then get some result of execution.
     *
     *  4. executeWithTransaction(Procedure) -  use it, if you want to execute some DATA-WRITE (blocking) logic
     *                                          with Persistence and don't want to get any result of execution.
     *
     * @param <T> type of affected entity.
     */
    public static class WrappedStatement<T> {

        private EntityManagerFactory factory;
        private T entity;

        WrappedStatement(EntityManagerFactory factory, T entity) {
            LOGGER.trace("was created new instance of WrappedStatement (entity type: " +
                    entity.getClass().getSimpleName() + ")");

            this.factory = factory;
            this.entity = entity;
        }

        /**
         * Executes logic (encapsulated by Function instance) on wrapped
         * args (factory and entity) AND returns execution result.
         *
         * @param function executable logic container (interface).
         * @return result of logic (function) execution, or null, if
         * operation was not successful.
         *
         * @see Function
         */
        public T execute(Function<T> function) {
            if (!factory.isOpen()) {
                LOGGER.error("factory is closed");
                return null;
            }

            EntityManager manager = factory.createEntityManager();

            try {
                T executionResult = function.execute(manager, entity);

                LOGGER.info("executed logic of " + function);
                return executionResult;
            } catch (Exception e) {
                LOGGER.error("can't execute logic of " + function, e);
            } finally {
                manager.close();
            }
            return null;
        }

        /**
         * Executes logic (encapsulated by Procedure instance) on wrapped
         * args (factory and entity) WITHOUT returning of execution result.
         *
         * @param procedure executable logic container (interface).
         *
         * @see Procedure
         */
        public void execute(Procedure<T> procedure) {
            if (!factory.isOpen()) {
                LOGGER.error("factory is closed");
            }

            EntityManager manager = factory.createEntityManager();

            try {
                procedure.execute(manager, entity);

                LOGGER.info("executed logic of " + procedure);
            } catch (Exception e) {
                LOGGER.error("can't execute logic of " + procedure, e);
            } finally {
                manager.close();
            }
        }

        /**
         * Executes logic (encapsulated by Function instance) on wrapped
         * args (factory and entity) AND returns execution result.
         * Note: this method uses EntityTransaction to execute change-data operation on Persistence!
         *
         * @param function executable logic container (interface).
         * @return result of logic (function) execution, or null, if
         * operation was not successful.
         *
         * @see Function
         */
        public T executeWithTransaction(Function<T> function) {
            if (!factory.isOpen()) {
                LOGGER.error("factory is closed");
                return null;
            }

            EntityManager manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            try {
                transaction.begin();
                T result = function.execute(manager, entity);
                transaction.commit();

                LOGGER.info("executed logic of " + function);
                return result;
            } catch (Exception e) {
                LOGGER.error("can't execute logic of " + function, e);
                transaction.rollback();
            } finally {
                manager.close();
            }
            return null;
        }



        /**
         * Executes logic (encapsulated by Procedure instance) on wrapped
         * args (factory and entity) WITHOUT returning of execution result.
         * Note: this method uses EntityTransaction to execute change-data operation on Persistence!
         *
         * @param procedure executable logic container (interface).
         *
         * @see Procedure
         */
        public void executeWithTransaction(Procedure<T> procedure) {
            if (!factory.isOpen()) {
                LOGGER.error("factory is closed");
            }

            EntityManager manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            try {
                transaction.begin();
                procedure.execute(manager, entity);
                transaction.commit();

                LOGGER.info("executed logic of " + procedure);
            } catch (Exception e) {
                LOGGER.error("can't execute logic of " + procedure, e);
                transaction.rollback();
            } finally {
                manager.close();
            }
        }
    }

    /**
     * Wrapper of executable logic and its arguments (factory and entity).
     *
     * Includes two different variants of statement execution. Here are usage advices:
     *
     *  1. execute(GroupFunction) -     use it, if you want to execute some DATA-READ (non-blocking) logic
     *                                  with Persistence and get multiple results (result List) of execution.
     *
     *  2. executeWithTransaction(FunGroupFunction) -   use it, if you want to execute some DATA-WRITE (blocking)
     *                                                  logic with Persistence and then get multiple results
     *                                                  (result List) of execution.
     *
     * @param <T> type of affected entity.
     */
    public static class GroupWrappedStatement<T> {

        private EntityManagerFactory factory;
        private T entity;
        private int limit;

        GroupWrappedStatement(EntityManagerFactory factory, T entity, int limit) {
            LOGGER.trace("was created new instance of GroupWrappedStatement (entity type: " +
                    entity.getClass().getSimpleName() + ")");

            this.factory = factory;
            this.entity = entity;
            this.limit = limit;
        }

        /**
         * Executes logic (encapsulated by GroupFunction instance) on wrapped
         * args (factory and entity) AND returns List of execution results.
         *
         * @param groupFunction executable logic container (interface).
         * @return List of results of logic (group function) execution, or null, if
         * operation was not successful.
         *
         * @see Function
         */
        public List<T> execute(GroupFunction<T> groupFunction) {
            if (!factory.isOpen()) {
                LOGGER.error("factory is closed");
                return null;
            }

            EntityManager manager = factory.createEntityManager();

            try {
                List<T> executionResult = groupFunction.execute(manager, entity, limit);

                LOGGER.info("executed logic of " + groupFunction);
                return executionResult;
            } catch (Exception e) {
                LOGGER.error("can't execute logic of " + groupFunction, e);
            } finally {
                manager.close();
            }
            return null;
        }

        /**
         * Executes logic (encapsulated by GroupFunction instance) on wrapped
         * args (factory and entity) AND returns List of execution results.
         * Note: this method uses EntityTransaction to execute change-data operation on Persistence!
         *
         * @param groupFunction executable logic container (interface).
         * @return List of results of logic (group function) execution, or null, if
         * operation was not successful.
         *
         * @see GroupFunction
         */
        public List<T> executeWithTransaction(GroupFunction<T> groupFunction) {
            if (!factory.isOpen()) {
                LOGGER.error("factory is closed");
                return null;
            }

            EntityManager manager = factory.createEntityManager();
            EntityTransaction transaction = manager.getTransaction();

            try {
                transaction.begin();
                List<T> result = groupFunction.execute(manager, entity, limit);
                transaction.commit();

                LOGGER.info("executed logic of " + groupFunction);
                return result;
            } catch (Exception e) {
                LOGGER.error("can't execute logic of " + groupFunction, e);
                transaction.rollback();
            } finally {
                manager.close();
            }
            return null;
        }
    }



    /**
     * Function interface - variant of Action logic wrapper,
     * which takes two args and return result.
     *
     * @param <T> type of affected entity.
     */
    public interface Function<T> {
        /**
         * Executes function's logic.
         *
         * @param manager first argument.
         * @param entity second argument.
         * @return execution result (with type of second argument).
         */
        T execute(EntityManager manager, T entity);
    }


    /**
     * Group function interface - variant of Action logic wrapper,
     * which takes two args and return List of results.
     *
     * @param <T> type of affected entity.
     */
    public interface GroupFunction<T> {
        /**
         * Executes function's logic.
         *
         * @param manager first argument.
         * @param entity second argument.
         * @param limit max number of results in List.
         * @return List of execution results (with type of second argument).
         */
        List<T> execute(EntityManager manager, T entity, int limit);
    }


    /**
     * Procedure interface - variant of Action logic wrapper,
     * which takes two args and doesn't return execution result.
     *
     * @param <T> type of affected entity.
     */
    public interface Procedure<T> {
        /**
         * Executes procedure's logic.
         *
         * @param manager first argument.
         * @param entity second argument.
         */
        void execute(EntityManager manager, T entity);
    }
}
