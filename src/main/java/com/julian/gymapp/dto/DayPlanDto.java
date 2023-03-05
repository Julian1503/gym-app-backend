package com.julian.gymapp.dto;

import com.julian.gymapp.domain.enums.Day;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayPlanDto {

  private Long dayPlanId;

  @NotNull(message = "the day must not be null")
  private Day dayName;

  private boolean finished;

  @NotNull(message = "You must put some exercise")
  @Size(min = 1, message = "You must put some exercise")
  private List<ExerciseDayPlanDto> exercisesOfADay;

  @NotNull(message = "The plan must not be null")
  private Long planId;
}
