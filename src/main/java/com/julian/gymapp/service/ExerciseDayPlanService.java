package com.julian.gymapp.service;

import com.julian.gymapp.domain.ExerciseDayPlan;
import com.julian.gymapp.repository.ExerciseDayPlanRepository;
import com.julian.gymapp.repository.IExerciseDayPlanRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ExerciseDayPlanService implements
    IExerciseDayPlanRepository {

  private final ExerciseDayPlanRepository exerciseDayPlanRepository;

  public ExerciseDayPlanService(ExerciseDayPlanRepository exerciseDayPlanRepository) {
    this.exerciseDayPlanRepository = exerciseDayPlanRepository;
  }

  @Override
  public List<ExerciseDayPlan> findAll() {
    return exerciseDayPlanRepository.findAllByIsDeletedFalse();
  }

  @Override
  public ExerciseDayPlan findById(Long id) {
    Optional<ExerciseDayPlan> exerciseDayPlan = exerciseDayPlanRepository.findByExercisesDayPlanIdAndIsDeletedFalse(id);
    return exerciseDayPlan.orElse(null);
  }

  @Override
  public void finishExercise(Long id) {
    ExerciseDayPlan exerciseDayPlan = exerciseDayPlanRepository.findByExercisesDayPlanIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("ExerciseDayPlan was not found"));
    exerciseDayPlan.setFinished(!exerciseDayPlan.isFinished());
    exerciseDayPlanRepository.save(exerciseDayPlan);
  }

  @Override
  public ExerciseDayPlan save(ExerciseDayPlan entity) {
    Optional<Short> orderOptional = exerciseDayPlanRepository.findMaxOrderNumberByDayAndIsDeletedFalse(entity.getDay());
    short order = orderOptional.orElse((short) 0);
    entity.setOrder((short) (order + 1));
    validateExerciseDayPlan(entity);
    return exerciseDayPlanRepository.save(entity);
  }

  @Override
  public ExerciseDayPlan update(ExerciseDayPlan entity, Long id) {
    ExerciseDayPlan exerciseDayPlan = exerciseDayPlanRepository.findByExercisesDayPlanIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("ExerciseDayPlan was not found"));
    validateExerciseDayPlan(exerciseDayPlan);
    exerciseDayPlan.setWeight(entity.getWeight());
    exerciseDayPlan.setDay(entity.getDay());
    exerciseDayPlan.setExercise(entity.getExercise());
    exerciseDayPlan.setRepetitions(entity.getRepetitions());
    exerciseDayPlan.setSeries(entity.getSeries());
    exerciseDayPlan.setWarmup(entity.isWarmup());
    exerciseDayPlan.setDuration(entity.getDuration());
    return exerciseDayPlanRepository.save(exerciseDayPlan);
  }

  protected void validateExerciseDayPlan(ExerciseDayPlan entity) {

  }

  @Override
  public void deleteById(Long id) {
    Optional<ExerciseDayPlan> exerciseDayPlan = exerciseDayPlanRepository.findByExercisesDayPlanIdAndIsDeletedFalse(id);
    if(exerciseDayPlan.isPresent()) {
      ExerciseDayPlan exerciseDayPlanToDelete = exerciseDayPlan.get();
      exerciseDayPlanToDelete.setDeleted(true);
      exerciseDayPlanRepository.save(exerciseDayPlanToDelete);

      // Find all ExerciseDayPlan for the same day with an order larger than the one deleted
      List<ExerciseDayPlan> exerciseDayPlansToReorder = exerciseDayPlanRepository.findByDayAndOrderGreaterThanAndIsDeletedFalse(
          exerciseDayPlanToDelete.getDay(), exerciseDayPlanToDelete.getOrder());

      // Decrease their order by one
      for(ExerciseDayPlan edp : exerciseDayPlansToReorder){
        edp.setOrder((short) (edp.getOrder() - 1));
        exerciseDayPlanRepository.save(edp);
      }
    } else {
      throw new IllegalArgumentException("ExerciseDayPlan was not found");
    }
  }

  @Override
  public List<ExerciseDayPlan> findAllByIsDeletedFalseAndPlanId(Long planId) {
    return exerciseDayPlanRepository.findAllByIsDeletedFalseAndPlanId(planId);
  }

  @Override
  public List<ExerciseDayPlan> findAllByIsDeletedFalseAndMemberId(Long memberId) {
    return exerciseDayPlanRepository.findAllByIsDeletedFalseAndMemberId(memberId);
  }

  @Override
  public void moveExerciseDayPlan(Long exerciseDayPlanId, Long newPlanId) {
    Optional<ExerciseDayPlan> eventToMoveOpt =
        exerciseDayPlanRepository.findById(exerciseDayPlanId);
    Optional<ExerciseDayPlan> eventToSwapOpt =
        exerciseDayPlanRepository.findById(newPlanId);

    if (eventToMoveOpt.isPresent() && eventToSwapOpt.isPresent()) {
      ExerciseDayPlan eventToMove = eventToMoveOpt.get();
      ExerciseDayPlan eventToSwap = eventToSwapOpt.get();

      short tempOrder = eventToMove.getOrder();
      eventToMove.setOrder(eventToSwap.getOrder());
      eventToSwap.setOrder(tempOrder);

      exerciseDayPlanRepository.save(eventToMove);
      exerciseDayPlanRepository.save(eventToSwap);
    }
  }
}
