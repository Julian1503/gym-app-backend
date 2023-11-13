package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterDto {
  private Long cashRegisterId;
  private BigDecimal initialBalance;
  private BigDecimal currentBalance;
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  @NotNull(message = "open date can not be null")
  private LocalDate openDate;
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate closeDate;
  private boolean isOpen;
  private BigDecimal difference;

  public BigDecimal getDifference() {
    return currentBalance.subtract(initialBalance);
  }
}
