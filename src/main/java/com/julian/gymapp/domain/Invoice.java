package com.julian.gymapp.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "invoice_id")
  private Long invoiceId;

  @Column(name = "invoice_number", unique = true, nullable = false)
  private String invoiceNumber;

  @Column(name = "invoice_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date invoiceDate;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
  private List<InvoiceItem> items;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Member client;

  @Column(name = "tax_rate")
  private BigDecimal taxRate;

  @Column(name = "total_amount")
  private BigDecimal totalAmount;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
  private List<Payment> payments;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}