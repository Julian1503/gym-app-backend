package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="series_register")
@NoArgsConstructor
@AllArgsConstructor
public class SeriesRegister {
  @Id
  @Column(name="series_register_id", nullable = false)
  private Long seriesRegisterId;
  @Column(name="repetitions", nullable = false, precision = 2)
  private Short repetitions;
  @Column(name="weight", nullable = false, precision = 3)
  private Short weight;
  @Column(name="rest", nullable = false, precision = 2)
  private Short rest;
  @Column(name="duration", nullable = false)
  private Short duration;
  @Column(name="exercises_day_plan_id")
  private Long exercisesDayPlanId;
  @Column(name="is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
  @Column(name="series_order", nullable = false, precision = 2)
  private Short order;
}
