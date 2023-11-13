package com.julian.gymapp.dto;

import com.julian.gymapp.domain.Invoice;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class PaymentDto {

  private Long paymentId;

  private Long invoiceId;

  private BigDecimal amount;

  private Date paymentDate;

}
