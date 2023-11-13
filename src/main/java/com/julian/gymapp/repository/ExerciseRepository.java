package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Exercise;
import com.julian.gymapp.domain.Exercise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  List<Exercise> findAllByIsDeletedFalse();
  Optional<Exercise> findByExerciseIdAndIsDeletedFalse(Long id);
}
