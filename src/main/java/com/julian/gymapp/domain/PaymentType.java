package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="payment_type")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentTypeId;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "paymentType")
  private List<Payment> payments;

  @OneToMany(mappedBy = "paymentType")
  private List<CashTransaction> cashTransactions;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;

  public PaymentType(Long paymentTypeId) {
    this.paymentTypeId = paymentTypeId;
  }
}