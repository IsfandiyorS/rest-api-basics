package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * Class {@code DatabaseConfigurationTest} contains spring configuration for dao subproject tests.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Profile("test")
@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database.properties")
public class DatabaseConfigurationTest {

    /**
     * Create bean {@link DataSource} which will be used as data source, which is updated before every run.
     *
     * @return the basicDataSource
     */
    @Bean
    public DataSource dataSource(@Value("${database.user}") String user,
                                 @Value("${database.password}") String password,
                                 @Value("${database.driver}") String className,
                                 @Value("${database.url}") String connectionUrl,
                                 @Value("${database.connection}") Integer connectionsNumber) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(className);
        basicDataSource.setUrl(connectionUrl);
        basicDataSource.setMaxActive(connectionsNumber);

        Resource initData = new ClassPathResource("creatingTestTables.sql");
        Resource fillData = new ClassPathResource("fillingTestTables.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData, fillData);
        DatabasePopulatorUtils.execute(databasePopulator, basicDataSource);
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