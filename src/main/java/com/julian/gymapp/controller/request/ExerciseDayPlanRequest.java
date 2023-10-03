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

    private boolean isFinished;

    @Min(value = 0, message = "Weight must be a positive number or zero")
    private Double weight;

    private Long exerciseId;

    @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate day;

    private Long planId;
}
