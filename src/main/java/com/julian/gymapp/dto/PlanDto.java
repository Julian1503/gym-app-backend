package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class PlanDto {
  private Long planId;

  @NotBlank(message = "Plan name cannot be blank")
  @Size(max = 100, message = "Plan name must be less than or equal to 100 characters")
  private String name;

  @NotEmpty(message = "Trainer list cannot be empty")
  private List<TrainerDto> trainers;

  @NotEmpty(message = "Day plan list cannot be empty")
  private List<DayPlanDto> dayPlans;

  @NotNull(message = "Member ID cannot be null")
  private Long memberId;
}