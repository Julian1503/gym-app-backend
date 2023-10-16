package com.julian.gymapp.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class InvoiceItemDto {

  private Long invoiceItemId;

  private Long invoiceId;

  private String description;

  private int quantity;

  private BigDecimal unitPrice;

  private BigDecimal subtotal;
}
