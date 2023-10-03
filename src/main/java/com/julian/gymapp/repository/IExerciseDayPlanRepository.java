package com.julian.gymapp.repository;

import com.julian.gymapp.domain.ExerciseDayPlan;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface IExerciseDayPlanRepository extends IBasicCrud<ExerciseDayPlan, Long> {
  List<ExerciseDayPlan> findAllByIsDeletedFalseAndPlanId(Long planId);
  List<ExerciseDayPlan> findAllByIsDeletedFalseAndMemberId(Long memberId);
  void moveExerciseDayPlan(Long exerciseDayPlanId, Long newPlanId);

  void finishExercise(Long id);
}
