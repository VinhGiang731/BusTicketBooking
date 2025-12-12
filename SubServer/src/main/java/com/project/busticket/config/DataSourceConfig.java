package com.project.busticket.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.project.busticket.enums.DataEnum;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DatabaseStatusProvider statusProvider;

    @Bean
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSourceProperties mysqlProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("custom.datasource.mysql")
    public DataSourceProperties subMysqlProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mysqlDataSource(@Qualifier("mysqlProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSource subMysqlDataSource(@Qualifier("subMysqlProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public DataSource routingDataSource(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
            @Qualifier("subMysqlDataSource") DataSource subMysqlDataSource) {

        DynamicRoutingDatabase routingDataSource = new DynamicRoutingDatabase();

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(DataEnum.MYSQL, mysqlDataSource);
        dataSources.put(DataEnum.SUBMYSQL, subMysqlDataSource);
        routingDataSource.setTargetDataSources(dataSources);

        if (statusProvider.isMySqlAvailable()) {
            routingDataSource.setDefaultTargetDataSource(mysqlDataSource);
        } else {
            routingDataSource.setDefaultTargetDataSource(subMysqlDataSource);
        }

        return routingDataSource;
    }
}
