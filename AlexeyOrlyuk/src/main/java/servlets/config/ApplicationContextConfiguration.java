package servlets.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by alex323glo on 24.02.18.
 */
@Configuration
@PropertySource(value = "classpath:/servlets-task/spring-context.properties")
@EnableTransactionManagement(proxyTargetClass = true)
public class ApplicationContextConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean =
                new LocalContainerEntityManagerFactoryBean();

        bean.setPersistenceXmlLocation(environment.getProperty("persistence.xml_path"));
        bean.setPersistenceUnitName(environment.getProperty("persistence.unit_name"));
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactoryBean().getObject());
    }

}
