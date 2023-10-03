package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Exercise;
import com.julian.gymapp.dto.ExerciseDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/exercise")
public class ExerciseController extends BaseController {

  private final IBasicCrud<Exercise, Long> exerciseRepository;
  private final ModelMapper mapper;

  public ExerciseController(IBasicCrud<Exercise, Long> exerciseRepository, ModelConfig mapper) {
    this.exerciseRepository = exerciseRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllExercises() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Exercise> exercises = exerciseRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(exercises), "Exercises returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Exercise exercise = exerciseRepository.findById(id);
      if (exercise == null) {
        baseResponse = createErrorResponse("Exercise was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(exercise), "Exercise returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createExercise(@Valid @RequestBody ExerciseDto exerciseDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Exercise exercise = mapper.map(exerciseDto, Exercise.class);
      Exercise savedExercise = exerciseRepository.save(exercise);
      baseResponse = createSuccessResponse(convertToDto(savedExercise), "Exercise was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateExercise(@Valid @RequestBody ExerciseDto exerciseDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Exercise exercise = mapper.map(exerciseDto, Exercise.class);
      Exercise updatedExercise = exerciseRepository.update(exercise, id);
      baseResponse = createSuccessResponse(convertToDto(updatedExercise), "Exercise was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteExercise(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      exerciseRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Exercise was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private ExerciseDto convertToDto(Exercise exercise) {
    return mapper.map(exercise, ExerciseDto.class);
  }

  private List<ExerciseDto> convertToDto(List<Exercise> exerciseList) {
    return exerciseList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
