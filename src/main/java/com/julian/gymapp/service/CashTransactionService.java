package com.julian.gymapp.service;

import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.repository.CashTransactionRepository;
import com.julian.gymapp.service.interfaces.ICashTransactionService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashTransactionService implements ICashTransactionService {

  private final CashTransactionRepository cashTransactionRepository;
  private final ModelMapper mapper;

  public CashTransactionService(CashTransactionRepository cashTransactionRepository, ModelConfig mapper) {
    this.cashTransactionRepository = cashTransactionRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<CashTransaction> findAll() {
    return cashTransactionRepository.findAllByIsDeletedFalse();
  }

  @Override
  public CashTransaction findById(Long id) {
    Optional<CashTransaction> dayPlan = cashTransactionRepository.findByCashTransactionIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  public List<CashTransaction> findByCashRegisterId(Long cashRegisterId) {
    return cashTransactionRepository.findByCashRegisterIdAndIsDeletedFalse(cashRegisterId);
  }

  @Override
  @Transactional
  public CashTransaction save(CashTransaction entity) {
    validateCashTransaction(entity);
    entity.setTransactionDate(new Date());
    return cashTransactionRepository.save(entity);
  }

  @Override
  public CashTransaction update(CashTransaction entity, Long id) {
    CashTransaction cashTransaction = cashTransactionRepository.findByCashTransactionIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("CashTransaction was not found"));
    validateCashTransaction(cashTransaction);
    cashTransaction.setAmount(entity.getAmount());
    cashTransaction.setCashRegister(entity.getCashRegister());
    cashTransaction.setTransactionDate(entity.getTransactionDate());
    cashTransaction.setDescription(entity.getDescription());
    return cashTransactionRepository.save(cashTransaction);
  }

  protected void validateCashTransaction(CashTransaction entity) {

  }

  @Override
  public void deleteById(Long id) {
    Optional<CashTransaction> cashTransaction = cashTransactionRepository.findByCashTransactionIdAndIsDeletedFalse(id);
    if(cashTransaction.isPresent()) {
      CashTransaction cashTransactionToDelete = cashTransaction.get();
      cashTransactionToDelete.setDeleted(true);
      cashTransactionRepository.save(cashTransactionToDelete);
    } else {
      throw new IllegalArgumentException("CashTransaction was not found");
    }
  }
}
