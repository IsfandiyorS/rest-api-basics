package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * This class contains Spring configuration for DAO subproject.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class DatabaseConfiguration {

    /**
     * Create bean {@link DataSource} which will be used as data source.
     *
     * @return the basicDataSource
     */
    @Bean
    public DataSource  dataSource(@Value("${db.url}") String url,
                                     @Value("${db.user}") String username,
                                     @Value("${db.password}") String password,
                                     @Value("${db.driver}") String driverName,
                                     @Value("${db.connection}") int connectionNumber) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(driverName);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxActive(connectionNumber);
        return basicDataSource;
    }

    /**
     * Create bean {@link JdbcTemplate} which will be used for queries to database.
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
