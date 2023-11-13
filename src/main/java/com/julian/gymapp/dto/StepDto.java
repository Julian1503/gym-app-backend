package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StepDto {
  private Long stepId;
  @NotNull(message = "Description cannot be null")
  private String description;
  @NotNull(message = "Order cannot be null")
  private Integer order;
}