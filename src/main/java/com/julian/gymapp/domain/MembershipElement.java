package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="membership_element")
@NoArgsConstructor
@AllArgsConstructor
public class MembershipElement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="element_id", nullable = false)
  private Long elementId;
  @Column(name="name", nullable = false, length = 100)
  private String name;
  @Column(name="description", length = 200)
  private String description;
  @Column(name="cost", nullable = false, precision = 5, scale = 2)
  private Double cost;
  @Column(name="photo", nullable = false)
  private Byte[] photo;
}