package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class TrainerDto extends PersonDto {
  @Size(max = 20, message = "Trainer number must be at most 50 characters\"")
  private String trainerNumber;
  @NotNull(message = "the hire date must not be null")
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate hireDate;

  List<SpecialtyDto> specialties;
}
