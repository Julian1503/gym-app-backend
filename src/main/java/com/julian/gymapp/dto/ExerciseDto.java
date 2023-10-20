package com.julian.gymapp.dto;

import com.julian.gymapp.domain.enums.MuscleGroup;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class ExerciseDto {
  private Long exerciseId;

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 150, message = "Name cannot exceed 150 characters")
  private String name;

  @NotBlank(message = "Description cannot be blank")
  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;

  @NotNull(message = "Muscle group cannot be null")
  private MuscleGroup muscleGroup;

  @Min(value = 1, message = "Difficulty level must be at least 1")
  @Max(value = 999, message = "Difficulty level cannot exceed 999")
  private Short difficultyLevel;

  private String photo;

  // Validation for collections
  @NotEmpty(message = "Steps cannot be empty")
  private List<StepDto> steps;

  private List<SpecialtyDto> specialties;

  private List<EquipmentDto> equipments;
}