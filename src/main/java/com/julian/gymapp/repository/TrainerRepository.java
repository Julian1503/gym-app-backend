package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Trainer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
  boolean existsByTrainerNumber(String trainerNumber);
  Optional<Trainer> findTrainerByTrainerNumber(String trainerNumber);
  Optional<Trainer> findByPersonIdAndIsDeletedFalse(Long id);
  List<Trainer> findAllByIsDeletedFalse();
}
