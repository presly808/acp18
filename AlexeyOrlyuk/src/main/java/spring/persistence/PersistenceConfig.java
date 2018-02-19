package spring.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * JPA Persistence configuration.
 *
 * Note: file "classpath:/spring-task/spring-app.properties" MUST contain such valid properties:
 *
 *  - "db.driver" - JDBC Driver full class name (property value example: "com.mysql.cj.jdbc.Driver");
 *  - "db.url" - valid URL to used DB (property value example: "jdbc:mysql://localhost:3306/spring_jpa");
 *  - "db.username" - valid username to access used DB (property value example: "root");
 *  - "db.password" - valid password (if it was set) to access used DB (property value example: "root");
 *
 *  - "hibernate.hbm2ddl.auto" - Hibernate settings for some auto validate/export actions with DDL
 *      (property value example: "create-drop");
 *  - "hibernate.dialect" - Hibernate SQL Dialect full class name
 *      (property value example: "org.hibernate.dialect.MySQL5Dialect").
 *
 * @author alex323glo
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:/spring-task/spring-app.properties")
public class PersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("spring.model");
        bean.setPersistenceUnitName("spring-h2-unit");
        bean.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");

        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setJpaProperties(additionalProperties());
        return bean;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
//        return new JpaTransactionManager(factory);
//    }

    @Bean
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        return properties;
    }
}
