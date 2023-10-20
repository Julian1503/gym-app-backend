package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Table(name = "cash_transaction")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CashTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cashTransactionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cash_register_id")
  private CashRegister cashRegister;

  @Column(nullable = false)
  private BigDecimal amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_type_id")
  private PaymentType paymentType;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date transactionDate;

  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "membership_id")
  private Membership membership;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}