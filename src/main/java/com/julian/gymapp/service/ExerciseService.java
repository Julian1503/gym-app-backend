package com.julian.gymapp.service;

import com.julian.gymapp.domain.Exercise;
import com.julian.gymapp.repository.ExerciseRepository;
import com.julian.gymapp.repository.StepRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseService implements IBasicCrud<Exercise, Long> {

  private final ExerciseRepository exerciseRepository;
  private final StepRepository stepRepository;
  private final ModelMapper mapper;

  public ExerciseService(ExerciseRepository exerciseRepository, StepRepository stepRepository,
      ModelConfig mapper) {
    this.exerciseRepository = exerciseRepository;
    this.stepRepository = stepRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<Exercise> findAll() {
    return exerciseRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Exercise findById(Long id) {
    Optional<Exercise> dayPlan = exerciseRepository.findByExerciseIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  @Transactional
  public Exercise save(Exercise entity) {
    validateExercise(entity);
    return exerciseRepository.save(entity);
  }

  @Override
  public Exercise update(Exercise entity, Long id) {
    Exercise exercise = exerciseRepository.findByExerciseIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Exercise was not found"));
    validateExercise(exercise);
    exercise.setSteps(entity.getSteps());
    exercise.setName(entity.getName());
    exercise.setDescription(entity.getDescription());
    exercise.setMuscleGroup(entity.getMuscleGroup());
    exercise.setDifficultyLevel(entity.getDifficultyLevel());
    exercise.setSpecialties(entity.getSpecialties());
    exercise.setEquipments(entity.getEquipments());
    exercise.setPhoto(entity.getPhoto());
    return exerciseRepository.save(exercise);
  }

  protected void validateExercise(Exercise entity) {
    if(entity.getName().isBlank() || entity.getName().isEmpty()) {
      throw new IllegalArgumentException("Exercise name must not be empty");
    }

    if(entity.getMuscleGroup() == null) {
      throw new IllegalArgumentException("You must select one muscle group");
    }

    if(entity.getDescription().isEmpty() || entity.getDescription().isBlank()) {
      throw new IllegalArgumentException("Description must not be empty");
    }

    if(entity.getDifficultyLevel() <= 0 && entity.getDifficultyLevel() < 11) {
      throw new IllegalArgumentException("The difficulty level must be between 1 and 10");
    }

    if(entity.getSpecialties() != null && entity.getSpecialties().isEmpty()) {
      throw new IllegalArgumentException("The exercise must have at least one specialty");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Exercise> exercise = exerciseRepository.findByExerciseIdAndIsDeletedFalse(id);
    if(exercise.isPresent()) {
      Exercise exerciseToDelete = exercise.get();
      exerciseToDelete.setDeleted(true);
      exerciseRepository.save(exerciseToDelete);
    } else {
      throw new IllegalArgumentException("Exercise was not found");
    }
  }
}