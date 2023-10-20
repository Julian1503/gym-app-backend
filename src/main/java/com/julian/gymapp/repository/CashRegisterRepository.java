package com.julian.gymapp.repository;

import com.julian.gymapp.domain.CashRegister;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {

  List<CashRegister> findAllByIsDeletedFalseOrderByOpenDateDesc();

  Optional<CashRegister> findByCashRegisterIdAndIsDeletedFalse(Long id);

  Optional<CashRegister> findByOpenDateAndIsDeletedFalse(Date openDate);

  @Query("SELECT c FROM CashRegister c WHERE c.openDate = (SELECT MAX(openDate) FROM CashRegister) AND c.isDeleted = false")
  Optional<CashRegister> getLastCashRegister();

  @Transactional
  @Modifying
  @Query(value = "UPDATE CashRegister c SET c.isOpen = false, c.closeDate = CURRENT_DATE WHERE c.isOpen = true")
  void closeCashRegisters();
}
