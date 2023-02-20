package com.julian.gymapp.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="trainer_specialty")
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSpecialty {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="trainer_specialty_id")
  private Long trainerSpecialtyId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trainer_id", nullable = false)
  private Trainer trainer;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "specialty_id", nullable = false)
  private Specialty specialty;
}
