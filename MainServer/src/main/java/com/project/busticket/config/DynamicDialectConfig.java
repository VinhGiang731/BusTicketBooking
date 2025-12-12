package com.project.busticket.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DynamicDialectConfig {

    private final DatabaseStatusProvider statusProvider;

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return (properties) -> {
            if (statusProvider.isMySqlAvailable()) {
                properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
            } else {
                properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
            }
        };
    }
}