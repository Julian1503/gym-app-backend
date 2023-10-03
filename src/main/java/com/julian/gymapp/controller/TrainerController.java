package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Trainer;
import com.julian.gymapp.dto.TrainerDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/trainer")
public class TrainerController extends BaseController {

  private final IBasicCrud<Trainer, Long> trainerRepository;
  private final ModelMapper mapper;
  public TrainerController(IBasicCrud<Trainer, Long> trainerRepository, ModelConfig mapper) {
    this.trainerRepository = trainerRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllTrainers() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Trainer> trainers = trainerRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(trainers), "Trainers returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Trainer trainer = trainerRepository.findById(id);
      if (trainer == null) {
        baseResponse = createErrorResponse("Trainer was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(trainer), "Trainer returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createTrainer(@Valid @RequestBody TrainerDto trainerDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Trainer trainer = mapper.map(trainerDto, Trainer.class);
      Trainer savedTrainer = trainerRepository.save(trainer);
      baseResponse = createSuccessResponse(convertToDto(savedTrainer), "Trainer was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateTrainer(@Valid @RequestBody TrainerDto trainerDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Trainer trainer = mapper.map(trainerDto, Trainer.class);
      Trainer updatedTrainer = trainerRepository.update(trainer, id);
      baseResponse = createSuccessResponse(convertToDto(updatedTrainer), "Trainer was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteTrainer(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      trainerRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Trainer was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private TrainerDto convertToDto(Trainer trainer) {
    return mapper.map(trainer, TrainerDto.class);
  }

  private List<TrainerDto> convertToDto(List<Trainer> trainers) {
    return trainers.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}