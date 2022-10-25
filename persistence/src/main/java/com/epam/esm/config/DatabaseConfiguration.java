package com.epam.esm.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
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

    @Bean
    public DataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public HikariConfig hikariConfig(@Value("${database.url}") String url,
                                     @Value("${database.user}") String username,
                                     @Value("${database.password}") String password,
                                     @Value("${database.driver}") String driverName,
                                     @Value("${database.connection}") int connectionNumber) {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName(driverName);
        config.setMaximumPoolSize(connectionNumber);
        return config;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
