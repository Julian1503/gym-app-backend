package com.julian.gymapp.domain;

import com.julian.gymapp.domain.enums.MuscleGroup;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_exercise_id_seq")
  @SequenceGenerator(name = "exercise_exercise_id_seq", sequenceName = "exercise_exercise_id_seq", allocationSize = 1)
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
  private String photo;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="exercise_id")
  private Set<Step> steps;
  @Column(name="is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;

  @ManyToMany
  @JoinTable(
      name = "exercise_specialty",
      joinColumns =  @JoinColumn(name="exercise_id"),
      inverseJoinColumns = @JoinColumn(name="specialty_id")
  )
  private Set<Specialty> specialties;

  @ManyToMany
  @JoinTable(
      name = "exercise_equipment",
      joinColumns =  @JoinColumn(name="exercise_id"),
      inverseJoinColumns = @JoinColumn(name="equipment_id")
  )
  private Set<Equipment> equipments;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="exercise_id")
  private Set<ExerciseDayPlan> dayPlans;

  public Exercise(Long exerciseId) {
    this.exerciseId = exerciseId;
  }
}
