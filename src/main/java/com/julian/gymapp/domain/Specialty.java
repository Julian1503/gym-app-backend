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
@Table(name="specialty")
@NoArgsConstructor
@AllArgsConstructor
public class Specialty {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="specialty_id", nullable = false)
  private Long specialtyId;
  @Column(name="name", nullable = false, length = 100)
  private String name;
  @Column(name="description", length = 200)
  private String description;
  @Column(name="photo")
  private Byte[] photo;
}