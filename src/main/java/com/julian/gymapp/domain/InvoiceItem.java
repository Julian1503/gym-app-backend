package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoice_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long invoiceItemId;

  @ManyToOne
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  @Column(name = "description")
  private String description;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "unit_price")
  private BigDecimal unitPrice;

  @Column(name = "subtotal")
  private BigDecimal subtotal;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}