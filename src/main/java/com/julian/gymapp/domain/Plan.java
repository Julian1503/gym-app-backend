package com.julian.gymapp.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_plan_id_seq")
  @SequenceGenerator(name = "plan_plan_id_seq", sequenceName = "plan_plan_id_seq", allocationSize = 1)
  @Column(name="plan_id", nullable = false)
  private Long planId;
  @Column(name="name", nullable = false, length = 100)
  private String name;
  @Column(name="member_id")
  private Long memberId;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
  @Column(name = "is_active", nullable = false, columnDefinition = "boolean default false")
  private boolean isActive;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "trainer_plan",
      joinColumns = @JoinColumn(name = "plan_id"),
      inverseJoinColumns = @JoinColumn(name = "trainer_id"))
  private Set<Trainer> trainers;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "plan_id")
  private Set<ExerciseDayPlan> dayPlans;
}
