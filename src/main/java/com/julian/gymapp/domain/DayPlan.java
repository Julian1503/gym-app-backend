package com.julian.gymapp.domain;

import com.julian.gymapp.domain.enums.Day;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="day_plan")
@NoArgsConstructor
@AllArgsConstructor
public class DayPlan {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="day_plan_id", nullable = false)
  private Long dayPlanId;
  @Column(name="day_name", nullable = false)
  @Enumerated(EnumType.STRING)
  private Day dayName;
  @Column(name="finished", nullable = false ,columnDefinition = "boolean default false")
  private boolean finished;

  @OneToMany(fetch = FetchType.LAZY)
  private List<ExerciseDayPlan> exercisesOfADay;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id", nullable = false)
  private Plan plan;
}
