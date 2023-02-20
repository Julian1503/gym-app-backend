package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="trainer")
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends Person {
  @Id
  @Column(name="person_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long personId;
  @Column(name="trainer_number", nullable = false, length = 20)
  private String trainerNumber;
  @Column(name="hire_date", nullable = false)
  private Date hireDate;

  @OneToMany(fetch = FetchType.LAZY)
  private List<TrainerSpecialty> specialties;
  @OneToMany(fetch = FetchType.LAZY)
  private List<Plan> plans;
}
