package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Step;
import com.julian.gymapp.dto.StepDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/step")
public class StepController extends BaseController {

  private final IBasicCrud<Step, Long> stepRepository;
  private final ModelMapper mapper;

  public StepController(IBasicCrud<Step, Long> stepRepository, ModelConfig mapper) {
    this.stepRepository = stepRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllSteps() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Step> steps = stepRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(steps), "Steps returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Step step = stepRepository.findById(id);
      if (step == null) {
        baseResponse = createErrorResponse("Step was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(step), "Step returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createStep(@Valid @RequestBody StepDto stepDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Step step = mapper.map(stepDto, Step.class);
      Step savedStep = stepRepository.save(step);
      baseResponse = createSuccessResponse(convertToDto(savedStep), "Step was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateStep(@Valid @RequestBody StepDto stepDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Step step = mapper.map(stepDto, Step.class);
      Step updatedStep = stepRepository.update(step, id);
      baseResponse = createSuccessResponse(convertToDto(updatedStep), "Step was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteStep(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      stepRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Step was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private StepDto convertToDto(Step step) {
    return mapper.map(step, StepDto.class);
  }

  private List<StepDto> convertToDto(List<Step> steps) {
    return steps.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
