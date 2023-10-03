package com.julian.gymapp.repository;

import com.julian.gymapp.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
}
