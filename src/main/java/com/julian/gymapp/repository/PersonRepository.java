package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
  boolean existsByIdentifier(String identifier);
}
