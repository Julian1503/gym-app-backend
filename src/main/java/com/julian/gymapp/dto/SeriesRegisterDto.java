package com.julian.gymapp.dto;

import lombok.Data;

@Data
public class SeriesRegisterDto {
  private Short repetitions;
  private Short weight;
  private String rest;
  private String duration;
  private Long exerciseDayPlanId;
  private Short order;
}
