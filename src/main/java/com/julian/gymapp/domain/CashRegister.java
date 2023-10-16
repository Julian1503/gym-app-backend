package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cash_register")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashRegister {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cashRegisterId;

  @Column(nullable = false)
  private BigDecimal initialBalance;

  @Column(nullable = false)
  private BigDecimal currentBalance;

  @Column(nullable = false)
  private Date openDate;

  @OneToMany(mappedBy = "cashRegister")
  private List<CashTransaction> transactions;

  @Column(name = "close_date")
  private Date closeDate;

  @Column(nullable = false, name = "is_open", columnDefinition = "boolean default false")
  private boolean isOpen;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;

  public CashRegister(BigDecimal initialBalance, BigDecimal currentBalance) {
    this.initialBalance = initialBalance;
    this.currentBalance = currentBalance;
  }

  public CashRegister(Long cashRegisterId) {
    this.cashRegisterId = cashRegisterId;
  }
}