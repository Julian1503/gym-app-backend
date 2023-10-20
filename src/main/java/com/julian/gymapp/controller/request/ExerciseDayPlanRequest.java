package com.julian.gymapp.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ExerciseDayPlanRequest {

    private Long exercisesDayPlanId;

    private boolean warmup;

    private boolean isFinished;

    private Long exerciseId;

    @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate day;

    private Long planId;
}
