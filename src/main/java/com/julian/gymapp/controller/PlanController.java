package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Plan;
import com.julian.gymapp.dto.PlanDto;
import com.julian.gymapp.service.interfaces.IPlanService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/plan")
public class PlanController extends BaseController {

  private final IPlanService planService;
  private final ModelMapper mapper;

  public PlanController(IPlanService planService, ModelConfig modelConfig) {
    this.planService = planService;
    this.mapper = modelConfig.getModelMapper();
  }

  @GetMapping("/get-all")
  @Transactional
  public ResponseEntity<BaseResponse> getAllPlans() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Plan> plans = planService.findAll();
      baseResponse = createSuccessResponse(convertToDto(plans), "Plans returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Plan plan = planService.findById(id);
      if(plan == null) {
        baseResponse = createErrorResponse("Plan was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(plan), "Plan returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get-by-member/{memberId}")
  public ResponseEntity<BaseResponse> getByPersonId(@PathVariable Long memberId) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Plan> plans = planService.findByMemberId(memberId);
      baseResponse = createSuccessResponse(convertToDto(plans), "Plans returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get-active-plan/{memberId}")
  public ResponseEntity<BaseResponse> getActivePlan(@PathVariable Long memberId) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Plan plan = planService.findActivePlan(memberId);
      baseResponse = createSuccessResponse(convertToDto(plan), "Plans returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createPlan(@Valid @RequestBody PlanDto planDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Plan plan = mapper.map(planDto, Plan.class);
      Plan planSaved = planService.save(plan);
      baseResponse = createSuccessResponse(convertToDto(planSaved), "Plan was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updatePlan(@Valid @RequestBody PlanDto planDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Plan plan = mapper.map(planDto, Plan.class);
      Plan planUpdated = planService.update(plan, id);
      baseResponse = createSuccessResponse(convertToDto(planUpdated), "Plan was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/{id}/activate")
  public ResponseEntity<BaseResponse> activatePlan(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Plan planUpdated = planService.activatePlan(id);
      baseResponse = createSuccessResponse(convertToDto(planUpdated), "Plan was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deletePlan(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      planService.deleteById(id);
      baseResponse = createSuccessResponse(null, "Plan was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private PlanDto convertToDto(Plan plan) {
    return mapper.map(plan, PlanDto.class);
  }

  private List<PlanDto> convertToDto(List<Plan> planList) {
    return planList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
