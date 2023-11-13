package com.julian.gymapp.repository;

import com.julian.gymapp.domain.ExerciseDayPlan;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExerciseDayPlanRepository extends JpaRepository<ExerciseDayPlan, Long> {
  List<ExerciseDayPlan> findAllByIsDeletedFalse();
  Optional<ExerciseDayPlan> findByExercisesDayPlanIdAndIsDeletedFalse(Long id);

  List<ExerciseDayPlan> findAllByIsDeletedFalseAndPlanId(Long planId);

  @Query(value = "SELECT e FROM ExerciseDayPlan e INNER JOIN Plan p ON p.planId = e.planId WHERE p.memberId = :memberId AND e.isDeleted = false")
  List<ExerciseDayPlan> findAllByIsDeletedFalseAndMemberId(Long memberId);

  @Query("SELECT MAX(e.order) FROM ExerciseDayPlan e WHERE e.day = :day AND e.isDeleted = false")
  Optional<Short> findMaxOrderNumberByDayAndIsDeletedFalse(@Param("day") Date day);

  List<ExerciseDayPlan> findByDayAndOrderGreaterThanAndIsDeletedFalse(Date day, short order);
}
