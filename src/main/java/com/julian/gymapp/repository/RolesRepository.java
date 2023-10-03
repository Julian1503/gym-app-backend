package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
  List<Role> findAllByNameIn(List<String> names);
}
