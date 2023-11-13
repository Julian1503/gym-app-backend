package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="discount")
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="discount_id", nullable = false)
  private Long discountId;
  @Column(name="percentage", nullable = false, precision = 2, scale = 2)
  private Double percentage;
  @Column(name="code", nullable = false, length = 100)
  private String code;
  @Column(name="expired", nullable = false, columnDefinition = "boolean default false")
  private boolean expired;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;

  @OneToMany(fetch = FetchType.LAZY)
  private List<MembershipSubscription> membershipSubscriptions;
}
