package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "cash_transaction")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CashTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cashTransactionId;

  @ManyToOne
  @JoinColumn(name = "cash_register_id")
  private CashRegister cashRegister;

  @Column(nullable = false)
  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "payment_type_id")
  private PaymentType paymentType;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date transactionDate;

  private String description;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}