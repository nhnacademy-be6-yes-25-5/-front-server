package com.nhnacademy.springexam.config;

import lombok.Getter;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Getter
@Configuration
public class DataSourceConfig {
    //private String driverClassName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://133.186.241.167:3306/nhn_exam_41";
    private String username = "nhn_exam_41";
    private String password = "sW7ebfiu!";
    private int maxTotal = 10;
    private int maxIdle = 10;
    private int minIdle = 3;
    private int setMaxWaitMillis = 2000;

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(5);

        basicDataSource.setMaxWaitMillis(setMaxWaitMillis);
        basicDataSource.setValidationQuery("SELECT 1");
        basicDataSource.setTestOnBorrow(true);

        return basicDataSource;
    }
}
