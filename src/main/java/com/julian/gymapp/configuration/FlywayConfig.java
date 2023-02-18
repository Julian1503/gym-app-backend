package com.julian.gymapp.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.flywaydb.core.Flyway;

@Configuration
public class FlywayConfig {
  @Autowired
  private DataSource dataSource;

  @Bean(initMethod = "migrate")
  public Flyway flyway() {
    return Flyway.configure()
        .dataSource(dataSource)
        .load();
  }
}