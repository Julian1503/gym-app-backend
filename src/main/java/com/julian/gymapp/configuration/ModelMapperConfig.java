package com.julian.gymapp.configuration;

import com.julian.gymapp.service.ModelMapperMapping;
import com.julian.gymapp.service.interfaces.ModelConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
  @Bean
  public ModelConfig getModelMapper() {
    return new ModelMapperMapping();
  }
}
