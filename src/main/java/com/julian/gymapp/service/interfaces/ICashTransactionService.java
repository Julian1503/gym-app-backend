package com.julian.gymapp.service.interfaces;

import com.julian.gymapp.domain.CashTransaction;
import java.util.List;

public interface ICashTransactionService extends IBasicCrud<CashTransaction, Long> {
  List<CashTransaction> findByCashRegisterId(Long cashRegisterId);
}
