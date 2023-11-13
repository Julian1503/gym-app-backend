package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="membership")
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
  @Id
  @Column(name="membership_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_membership_id_seq")
  @SequenceGenerator(name = "membership_membership_id_seq", sequenceName = "membership_membership_id_seq", allocationSize = 1)
  private Long membershipId;
  @Column(name="name", nullable = false, length = 50)
  private String name;
  @Column(name="description", length = 200)
  private String description;
  @Column(name="days", precision = 4)
  private Short days;
  @Column(name="price")
  private Double price;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}
