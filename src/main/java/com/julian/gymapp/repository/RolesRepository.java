package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {

}
