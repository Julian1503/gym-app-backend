package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="exercise_day_plan")
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDayPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_day_plan_seq")
  @SequenceGenerator(name = "exercise_day_plan_seq", sequenceName = "exercise_day_plan_seq", allocationSize = 1)
  @Column(name="exercises_day_plan_id", nullable = false)
  private Long exercisesDayPlanId;
  @Column(name="exercise_order", nullable = false, precision = 2)
  private Short order;
  @Column(name="duration", nullable = false)
  private Time duration;
  @Column(name="repetitions", nullable = false, precision = 2)
  private Short repetitions;
  @Column(name="series", nullable = false, precision = 2)
  private Short series;
  @Column(name="warmup", nullable = false, columnDefinition = "boolean default false")
  private boolean warmup;
  @Column(name="finished", nullable = false, columnDefinition = "boolean default false")
  private boolean isFinished;
  @Column(name="weight")
  private Double weight;
  @Column(name="plan_id")
  private Long planId;
  @Column(name="day")
  private Date day;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;
}
