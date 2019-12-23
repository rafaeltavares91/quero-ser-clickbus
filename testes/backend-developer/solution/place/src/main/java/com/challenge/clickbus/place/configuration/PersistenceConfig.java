package com.challenge.clickbus.place.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.challenge.clickbus.place.repository"})
@EnableJpaAuditing
@EntityScan(basePackages = {"com.challenge.clickbus.place.domain"})
public class PersistenceConfig {
}
