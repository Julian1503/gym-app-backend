package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StepDto {
  private Long stepId;
  @NotNull(message = "Description cannot be null")
  private String description;
  @NotNull(message = "Order cannot be null")
  private Integer order;
  private Long exerciseId;

  public StepDto(Long stepId, String description, Integer order, Long exerciseId) {
    this.stepId = stepId;
    this.description = description;
    this.order = order;
    this.exerciseId = exerciseId;
  }
}