package com.julian.gymapp.repository;

import com.julian.gymapp.domain.SeriesRegister;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRegisterRepository extends JpaRepository<SeriesRegister,Long> {
  List<SeriesRegister> findAllByIsDeletedFalse();
  Optional<SeriesRegister> findBySeriesRegisterIdAndIsDeletedFalse(Long id);
  List<SeriesRegister> findByExercisesDayPlanIdAndIsDeletedFalse(Long exercisesDayPlanId);
}
