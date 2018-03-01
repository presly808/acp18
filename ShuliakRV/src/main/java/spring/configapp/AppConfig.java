package spring.configapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class AppConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("spring.model");
        bean.setPersistenceUnitName("spring-unit");
        bean.setPersistenceXmlLocation("classpath:/META-INF/persistence.xml");
        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return bean;
    }
}
