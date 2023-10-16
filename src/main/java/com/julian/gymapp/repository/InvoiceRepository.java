package com.julian.gymapp.repository;

import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.domain.Invoice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  Optional<Invoice> findByInvoiceIdAndIsDeletedFalse(Long id);

  List<Invoice> findAllByIsDeletedFalse();
}
