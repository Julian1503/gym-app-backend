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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "step")
public class Step {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_step_id_seq")
  @SequenceGenerator(name = "step_step_id_seq", sequenceName = "step_step_id_seq", allocationSize = 1)
  @Column(name="step_id", nullable = false)
  private Long stepId;
  @Column(name="description", nullable = false)
  private String description;
  @Column(name="step_order", nullable = false)
  private Integer order;
}
