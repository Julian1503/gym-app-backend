package com.julian.gymapp.service.interfaces;

import com.julian.gymapp.domain.CashRegister;
import java.math.BigDecimal;

public interface ICashRegisterService extends IBasicCrud<CashRegister, Long> {
  CashRegister getLastBalance();

  CashRegister closeCashRegister(Long id);
}
