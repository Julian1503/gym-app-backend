package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;

@Data
public class TrainerDto extends PersonDto {
  @Size(max = 20, message = "Trainer number must be at most 50 characters\"")
  private String trainerNumber;
  @NotNull(message = "the hire date must not be null")
  private Date hireDate;
}
