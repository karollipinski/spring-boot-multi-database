package com.example.springmultidatabase.configuration;

import com.example.springmultidatabase.model.postgresql.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "postgresqlEntityManager",
                       transactionManagerRef = "postgresqlTransactionManager",
                       basePackages = "com.example.springmultidatabase.repository.postgresql")
public class PostgresqlConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.postgresql.datasource")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                                .build();

    }

    @Primary
    @Bean(name = "postgresqlEntityManager")
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(postgresqlDataSource())
                      .properties(hibernateProperties())
                      .packages(Book.class)
                      .persistenceUnit("postgresPU")
                      .build();
    }

    private Map<String, Object> hibernateProperties() {
        Resource resource = new ClassPathResource("hibernate.properties");
        try {

            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet()
                             .stream()
                             .collect(Collectors.toMap(e -> e.getKey()
                                                             .toString(),
                                                       Map.Entry::getValue));

        } catch (IOException e) {
            return new HashMap<>();

        }
    }

    @Primary
    @Bean(name = "postgresqlTransactionManager")
    public PlatformTransactionManager postgresqlTransactionManager(@Qualifier("postgresqlEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}


