package com.julian.gymapp.controller;

import com.julian.gymapp.controller.request.ExerciseDayPlanRequest;
import com.julian.gymapp.controller.request.OrderUpdateRequest;
import com.julian.gymapp.controller.request.SeriesRegisterRequest;
import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Exercise;
import com.julian.gymapp.domain.ExerciseDayPlan;
import com.julian.gymapp.domain.SeriesRegister;
import com.julian.gymapp.dto.ExerciseDayPlanDto;
import com.julian.gymapp.dto.SeriesRegisterDto;
import com.julian.gymapp.repository.IExerciseDayPlanRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/api/exercise-day-plan")
public class ExerciseDayPlanController extends BaseController {

  private final IExerciseDayPlanRepository exerciseDayPlanRepository;
  private final ModelMapper mapper;

  public ExerciseDayPlanController(IExerciseDayPlanRepository exerciseDayPlanRepository, ModelConfig mapper) {
    this.exerciseDayPlanRepository = exerciseDayPlanRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllExerciseDayPlans() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<ExerciseDayPlan> exerciseDayPlans = exerciseDayPlanRepository.findAll();
      List<ExerciseDayPlanDto> exerciseDayPlanDtos = convertToDto(exerciseDayPlans);
      baseResponse = createSuccessResponse(exerciseDayPlanDtos, "ExerciseDayPlans returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get-by-plan/{planId}")
  public ResponseEntity<BaseResponse> getAllExerciseByPlan(@PathVariable Long planId) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<ExerciseDayPlan> exerciseDayPlans = exerciseDayPlanRepository.findAllByIsDeletedFalseAndPlanId(planId);
      List<ExerciseDayPlanDto> exerciseDayPlanDtos = convertToDto(exerciseDayPlans);
      baseResponse = createSuccessResponse(exerciseDayPlanDtos, "ExerciseDayPlans returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get-by-member/{memberId}")
  public ResponseEntity<BaseResponse> getAllExerciseByMember(@PathVariable Long memberId) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<ExerciseDayPlan> exerciseDayPlans = exerciseDayPlanRepository.findAllByIsDeletedFalseAndMemberId(memberId);
      List<ExerciseDayPlanDto> exerciseDayPlanDtos = convertToDto(exerciseDayPlans);
      baseResponse = createSuccessResponse(exerciseDayPlanDtos, "ExerciseDayPlans returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      ExerciseDayPlan exerciseDayPlan = exerciseDayPlanRepository.findById(id);
      if (exerciseDayPlan == null) {
        baseResponse = createErrorResponse("ExerciseDayPlan was not found");
      } else {
        ExerciseDayPlanDto exerciseDayPlanDto = convertToDto(exerciseDayPlan);
        baseResponse = createSuccessResponse(exerciseDayPlanDto, "ExerciseDayPlan returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createExerciseDayPlan(@Valid @RequestBody ExerciseDayPlanRequest exerciseDayPlanDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      ExerciseDayPlan exerciseDayPlan = map(exerciseDayPlanDto);
      exerciseDayPlan.setDay(java.sql.Date.valueOf(exerciseDayPlanDto.getDay()));
      exerciseDayPlan.setExercise(new Exercise(exerciseDayPlanDto.getExerciseId()));
      exerciseDayPlanRepository.save(exerciseDayPlan);
      ExerciseDayPlanDto createdExerciseDayPlanDto = convertToDto(exerciseDayPlan);
      baseResponse = createSuccessResponse(createdExerciseDayPlanDto, "ExerciseDayPlan was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update-order")
  public ResponseEntity<Void> updateOrder(@RequestBody @Valid OrderUpdateRequest request) {
      exerciseDayPlanRepository.moveExerciseDayPlan(request.getEventToMove(), request.getEventToSwap());
      return ResponseEntity.ok().build();
  }

  @PutMapping("/update/{dayPlanId}")
  public ResponseEntity<BaseResponse> updateExerciseDayPlan(@Valid @RequestBody ExerciseDayPlanRequest exerciseDayPlanDto, @PathVariable Long dayPlanId) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      ExerciseDayPlan exerciseDayPlan = map(exerciseDayPlanDto);
      exerciseDayPlan.setDay(java.sql.Date.valueOf(exerciseDayPlanDto.getDay()));
      exerciseDayPlan.setExercise(new Exercise(exerciseDayPlanDto.getExerciseId()));
      ExerciseDayPlan updatedExerciseDayPlan = exerciseDayPlanRepository.update(exerciseDayPlan, dayPlanId);
      ExerciseDayPlanDto updatedExerciseDayPlanDto = convertToDto(updatedExerciseDayPlan);
      baseResponse = createSuccessResponse(updatedExerciseDayPlanDto, "ExerciseDayPlan was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/finish/{dayPlanId}")
  public ResponseEntity<BaseResponse> finishExerciseDayPlan(@PathVariable Long dayPlanId, @RequestBody List<SeriesRegisterDto> seriesRegistersDto) {
    try {
      List<SeriesRegister> seriesRegisters = seriesRegistersDto.stream().map(s -> mapper.map(s,
          SeriesRegister.class)).collect(Collectors.toList());
      exerciseDayPlanRepository.finishExercise(dayPlanId, seriesRegisters);
    } catch (Exception e) {
      return createErrorResponse(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  @PutMapping("/restart/{dayPlanId}")
  public ResponseEntity<BaseResponse> restartExerciseDayPlan(@PathVariable Long dayPlanId) {
    try {
      exerciseDayPlanRepository.restartExercise(dayPlanId);
    } catch (Exception e) {
      return createErrorResponse(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  private ExerciseDayPlan map(ExerciseDayPlanRequest exerciseDayPlanDto) {
    return mapper.map(exerciseDayPlanDto, ExerciseDayPlan.class);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteExerciseDayPlan(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      exerciseDayPlanRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "ExerciseDayPlan was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private ExerciseDayPlanDto convertToDto(ExerciseDayPlan exerciseDayPlan) {
     ExerciseDayPlanDto exerciseDayPlanDto = mapper.map(exerciseDayPlan, ExerciseDayPlanDto.class);
     return exerciseDayPlanDto;
  }

  private List<ExerciseDayPlanDto> convertToDto(List<ExerciseDayPlan> exerciseDayPlans) {
    return exerciseDayPlans.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }
}
