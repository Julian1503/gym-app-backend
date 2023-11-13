package com.julian.gymapp.repository;

import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.domain.InvoiceItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

  Optional<InvoiceItem> findByInvoiceItemIdAndIsDeletedFalse(Long id);

  List<InvoiceItem> findAllByIsDeletedFalse();
}
