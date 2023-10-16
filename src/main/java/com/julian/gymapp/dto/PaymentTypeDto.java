package com.julian.gymapp.dto;

import java.util.List;
import lombok.Data;

@Data
public class PaymentTypeDto {
  private Long paymentTypeId;

  private String name;

  private List<InvoiceDto> invoices;
}
