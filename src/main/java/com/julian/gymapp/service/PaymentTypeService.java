package com.julian.gymapp.service;

import com.julian.gymapp.domain.PaymentType;
import com.julian.gymapp.repository.PaymentTypeRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentTypeService implements IBasicCrud<PaymentType, Long> {

  private final PaymentTypeRepository paymentTypeRepository;
  private final ModelMapper mapper;

  public PaymentTypeService(PaymentTypeRepository paymentTypeRepository, ModelConfig mapper) {
    this.paymentTypeRepository = paymentTypeRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<PaymentType> findAll() {
    return paymentTypeRepository.findAllByIsDeletedFalse();
  }

  @Override
  public PaymentType findById(Long id) {
    Optional<PaymentType> dayPlan = paymentTypeRepository.findByPaymentTypeIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  @Transactional
  public PaymentType save(PaymentType entity) {
    validatePaymentType(entity);
    return paymentTypeRepository.save(entity);
  }

  @Override
  public PaymentType update(PaymentType entity, Long id) {
    PaymentType paymentType = paymentTypeRepository.findByPaymentTypeIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("PaymentType was not found"));
    validatePaymentType(paymentType);
    paymentType.setName(entity.getName());
    return paymentTypeRepository.save(paymentType);
  }

  protected void validatePaymentType(PaymentType entity) {

  }

  @Override
  public void deleteById(Long id) {
    Optional<PaymentType> paymentType = paymentTypeRepository.findByPaymentTypeIdAndIsDeletedFalse(id);
    if(paymentType.isPresent()) {
      PaymentType paymentTypeToDelete = paymentType.get();
      paymentTypeToDelete.setDeleted(true);
      paymentTypeRepository.save(paymentTypeToDelete);
    } else {
      throw new IllegalArgumentException("PaymentType was not found");
    }
  }
}
