package com.julian.gymapp.dto;

import com.julian.gymapp.domain.Member;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class InvoiceDto {

  private Long invoiceId;

  private String invoiceNumber;

  private Date invoiceDate;

  private List<InvoiceItemDto> items;

  private Long clientId;

  private BigDecimal taxRate;

  private BigDecimal totalAmount;

  private Long paymentId;

}
