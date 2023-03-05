package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSpecialty {
  private Long trainerSpecialtyId;
  @NotNull(message = "Trainer ID is required")
  private Long trainerId;
  private String trainerName;
  @NotNull(message = "Specialty ID is required")
  private Long specialtyId;
  private String specialtyName;
}