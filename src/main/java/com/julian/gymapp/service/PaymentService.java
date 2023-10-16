package com.julian.gymapp.service;

import com.julian.gymapp.domain.Payment;
import com.julian.gymapp.repository.PaymentRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IBasicCrud<Payment, Long> {

  private final PaymentRepository paymentRepository;
  private final ModelMapper mapper;

  public PaymentService(PaymentRepository paymentRepository,ModelConfig mapper) {
    this.paymentRepository = paymentRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<Payment> findAll() {
    return paymentRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Payment findById(Long id) {
    Optional<Payment> dayPlan = paymentRepository.findByPaymentIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  @Transactional
  public Payment save(Payment entity) {
    validatePayment(entity);
    return paymentRepository.save(entity);
  }

  @Override
  public Payment update(Payment entity, Long id) {
    Payment payment = paymentRepository.findByPaymentIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Payment was not found"));
    validatePayment(payment);
    payment.setPaymentDate(entity.getPaymentDate());
    payment.setAmount(entity.getAmount());
    payment.setInvoice(entity.getInvoice());
    return paymentRepository.save(payment);
  }

  protected void validatePayment(Payment entity) {

  }

  @Override
  public void deleteById(Long id) {
    Optional<Payment> payment = paymentRepository.findByPaymentIdAndIsDeletedFalse(id);
    if(payment.isPresent()) {
      Payment paymentToDelete = payment.get();
      paymentToDelete.setDeleted(true);
      paymentRepository.save(paymentToDelete);
    } else {
      throw new IllegalArgumentException("Payment was not found");
    }
  }
}