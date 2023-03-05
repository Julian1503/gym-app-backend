package com.julian.gymapp.domain;

import com.julian.gymapp.domain.enums.MuscleGroup;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="exercise")
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="exercise_id", nullable = false)
  private Long exerciseId;
  @Column(name="name", nullable = false, length = 150)
  private String name;
  @Column(name="description", nullable = false, length = 500)
  private String description;
  @Column(name="muscle_group", nullable = false)
  @Enumerated(EnumType.STRING)
  private MuscleGroup muscleGroup;
  @Column(name="difficulty_level", nullable = false, precision = 3)
  private Short difficultyLevel;
  @Column(name="photo")
  private Byte[] photo;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Step> steps;
  @OneToMany(fetch = FetchType.LAZY)
  private List<ExerciseSpecialty> specialties;
  @OneToMany(fetch = FetchType.LAZY)
  private List<Equipment> equipmentList;
  @OneToMany(fetch = FetchType.LAZY)
  private List<ExerciseDayPlan> exerciseDayPlans;
}
