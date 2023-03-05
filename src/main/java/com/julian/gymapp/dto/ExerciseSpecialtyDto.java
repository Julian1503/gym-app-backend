package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExerciseSpecialtyDto {
  @NotNull(message = "Exercise ID cannot be null")
  private Long exerciseId;

  @NotNull(message = "Specialty ID cannot be null")
  private Long specialtyId;
}