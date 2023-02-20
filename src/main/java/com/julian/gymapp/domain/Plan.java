package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="plan")
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="plan_id", nullable = false)
  private Long planId;
  @Column(name="name", nullable = false, length = 100)
  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Trainer> trainer;
  @OneToMany(fetch = FetchType.LAZY)
  private List<DayPlan> dayPlans;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="person_id", nullable = false)
  private Member member;
}
