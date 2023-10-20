package com.julian.gymapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class CashTransactionDto {
  private Long cashTransactionId;
  private Long cashRegisterId;
  private BigDecimal amount;
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate transactionDate;
  private String description;
  private Long paymentTypeId;
  private Long memberId;
  private String memberLastname;
  private String memberName;
  private String memberIdentifier;
  private String membershipName;
  private Long membershipId;
  private String paymentTypeName;
}
