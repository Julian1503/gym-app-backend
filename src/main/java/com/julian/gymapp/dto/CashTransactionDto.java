package com.julian.gymapp.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class CashTransactionDto {
  private Long cashTransactionId;
  private Long cashRegisterId;
  private BigDecimal amount;
  private Date transactionDate;
  private String description;
  private Long paymentTypeId;
}
