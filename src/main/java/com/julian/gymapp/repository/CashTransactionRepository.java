package com.julian.gymapp.repository;

import com.julian.gymapp.domain.CashTransaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CashTransactionRepository extends JpaRepository<CashTransaction, Long> {

  Optional<CashTransaction> findByCashTransactionIdAndIsDeletedFalse(Long id);

  List<CashTransaction> findAllByIsDeletedFalse();

  @Query("SELECT c FROM CashTransaction c WHERE c.cashRegister.cashRegisterId = ?1 AND c.isDeleted = false")
  List<CashTransaction> findByCashRegisterIdAndIsDeletedFalse(Long cashRegisterId);
}
