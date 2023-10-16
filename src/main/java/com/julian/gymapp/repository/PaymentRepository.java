package com.julian.gymapp.repository;

import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.domain.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

  List<Payment> findAllByIsDeletedFalse();

  Optional<Payment> findByPaymentIdAndIsDeletedFalse(Long id);
}
