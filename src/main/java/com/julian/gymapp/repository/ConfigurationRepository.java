package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
  Configuration findByUser(Long userId);
}
