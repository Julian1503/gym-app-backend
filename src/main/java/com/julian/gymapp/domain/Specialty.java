package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialty_specialty_id_seq")
  @SequenceGenerator(name = "specialty_specialty_id_seq", sequenceName = "specialty_specialty_id_seq", allocationSize = 1)
  @Column(name="specialty_id", nullable = false)
  private Long specialtyId;
  @Column(name="name", nullable = false, length = 100)
  private String name;
  @Column(name="description", length = 200)
  private String description;
  @Column(name="photo")
  private Byte[] photo;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}