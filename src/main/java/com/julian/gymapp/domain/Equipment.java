package com.julian.gymapp.domain;

import com.julian.gymapp.domain.enums.EquipmentType;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="equipment")
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="equipment_id", nullable = false)
  private Long equipmentId;
  @Column(name="name", nullable = false, length = 80)
  private String name;
  @Column(name="type", nullable = false)
  @Enumerated(EnumType.STRING)
  private EquipmentType type;
  @Column(name="quantity", precision = 5)
  private Short quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false)
  private Exercise exercise;
}
