package com.julian.gymapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDayPlanDto {

  private Long exercisesDayPlanId;

  @NotNull(message = "Order cannot be null")
  @PositiveOrZero(message = "Order must be a positive number or zero")
  private Short order;

  @NotNull(message = "Duration cannot be null")
  private Time duration;

  @NotNull(message = "Repetitions cannot be null")
  @Positive(message = "Repetitions must be a positive number")
  private Short repetitions;

  @NotNull(message = "Series cannot be null")
  @Positive(message = "Series must be a positive number")
  private Short series;

  private boolean warmup;

  private boolean finished;

  @Min(value = 0, message = "Weight must be a positive number or zero")
  private Double weight;

  @NotNull(message = "Exercise cannot be null")
  private Long exerciseId;

  @NotNull(message = "Day plan cannot be null")
  private Long dayPlanId;

}