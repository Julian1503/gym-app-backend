package com.julian.gymapp.service;

import com.julian.gymapp.domain.CashRegister;
import com.julian.gymapp.repository.CashRegisterRepository;
import com.julian.gymapp.service.interfaces.ICashRegisterService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashRegisterService implements ICashRegisterService {

  private final CashRegisterRepository cashRegisterRepository;
  private final ModelMapper mapper;

  public CashRegisterService(CashRegisterRepository cashRegisterRepository, ModelConfig mapper) {
    this.cashRegisterRepository = cashRegisterRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<CashRegister> findAll() {
    return cashRegisterRepository.findAllByIsDeletedFalseOrderByOpenDateDesc();
  }

  @Override
  public CashRegister getLastBalance() {
    return cashRegisterRepository.getLastCashRegister()
        .orElse(new CashRegister(BigDecimal.ZERO, BigDecimal.ZERO));
  }

  @Override
  public CashRegister closeCashRegister(Long id) {
    CashRegister cashRegister = cashRegisterRepository.findByCashRegisterIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("CashRegister was not found"));
    cashRegister.setCloseDate(new Date());
    cashRegister.setOpen(false);
    return cashRegisterRepository.save(cashRegister);
  }

  @Override
  public CashRegister findById(Long id) {
    Optional<CashRegister> dayPlan = cashRegisterRepository.findByCashRegisterIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  @Transactional
  public CashRegister save(CashRegister entity) {
    validateCashRegister(entity);
    cashRegisterRepository.closeCashRegisters();
    return cashRegisterRepository.save(entity);
  }

  @Override
  public CashRegister update(CashRegister entity, Long id) {
    CashRegister cashRegister = cashRegisterRepository.findByCashRegisterIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("CashRegister was not found"));
    validateCashRegister(cashRegister);
    cashRegister.setInitialBalance(entity.getInitialBalance());
    cashRegister.setCurrentBalance(entity.getCurrentBalance());
    return cashRegisterRepository.save(cashRegister);
  }

  protected void validateCashRegister(CashRegister entity) {
    cashRegisterRepository.findByOpenDateAndIsDeletedFalse(entity.getOpenDate()).ifPresent(cashRegister -> {
      throw new IllegalArgumentException("Cash Register already exists");
    });
  }

  @Override
  public void deleteById(Long id) {
    Optional<CashRegister> cashRegister = cashRegisterRepository.findByCashRegisterIdAndIsDeletedFalse(id);
    if(cashRegister.isPresent()) {
      CashRegister cashRegisterToDelete = cashRegister.get();
      cashRegisterToDelete.setDeleted(true);
      cashRegisterRepository.save(cashRegisterToDelete);
    } else {
      throw new IllegalArgumentException("CashRegister was not found");
    }
  }
}