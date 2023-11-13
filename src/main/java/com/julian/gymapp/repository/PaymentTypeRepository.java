package com.julian.gymapp.repository;

import com.julian.gymapp.domain.PaymentType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

  List<PaymentType> findAllByIsDeletedFalse();

  Optional<PaymentType> findByPaymentTypeIdAndIsDeletedFalse(Long id);
}
