package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Specialty;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty,Long> {
  List<Specialty> findAllByIsDeletedFalse();
  Optional<Specialty> findBySpecialtyIdAndIsDeletedFalse(Long id);
}
