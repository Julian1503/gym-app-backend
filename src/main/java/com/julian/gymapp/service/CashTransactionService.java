package com.julian.gymapp.service;

import com.julian.gymapp.domain.CashRegister;
import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.domain.Membership;
import com.julian.gymapp.domain.MembershipSubscription;
import com.julian.gymapp.repository.CashRegisterRepository;
import com.julian.gymapp.repository.CashTransactionRepository;
import com.julian.gymapp.repository.MembershipRepository;
import com.julian.gymapp.repository.MembershipSubscriptionRepository;
import com.julian.gymapp.service.interfaces.ICashTransactionService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashTransactionService implements ICashTransactionService {

  private final CashTransactionRepository cashTransactionRepository;
  private final CashRegisterRepository cashRegisterRepository;
  private final MembershipSubscriptionRepository membershipSubscriptionRepository;
  private final MembershipRepository membershipRepository;
  private final ModelMapper mapper;

  public CashTransactionService(CashTransactionRepository cashTransactionRepository,
      CashRegisterRepository cashRegisterRepository,
      MembershipSubscriptionService membershipSubscriptionService,
      MembershipSubscriptionRepository membershipSubscriptionRepository,
      MembershipRepository membershipRepository, ModelConfig mapper) {
    this.cashTransactionRepository = cashTransactionRepository;
    this.cashRegisterRepository = cashRegisterRepository;
    this.membershipSubscriptionRepository = membershipSubscriptionRepository;
    this.membershipRepository = membershipRepository;
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
    CashRegister cashRegister = cashRegisterRepository.getReferenceById(entity.getCashRegister().getCashRegisterId());
    cashRegister.setCurrentBalance(cashRegister.getCurrentBalance().add(entity.getAmount()));
    cashRegisterRepository.save(cashRegister);
    Membership membership = membershipRepository.findByMembershipIdAndIsDeletedFalse(entity.getMembership().getMembershipId()).orElseThrow(() -> new EntityNotFoundException("Membership was not found"));
    MembershipSubscription membershipSubscription = membershipSubscriptionRepository.findLatestMembershipSubscriptionByMemberIdAndIsDeletedFalse(entity.getMember().getPersonId()).orElse(new MembershipSubscription());
    membershipSubscription.setMembershipId(entity.getMembership().getMembershipId());
    membershipSubscription.setMemberId(entity.getMember().getPersonId());
    membershipSubscription.setAmount(Short.parseShort("1"));
    membershipSubscription.setSubscriptionStart(membershipSubscription.getSubscriptionExpires() == null ? new Date() : membershipSubscription.getSubscriptionExpires());
    membershipSubscription.setSubscriptionExpires(membershipSubscription.getSubscriptionExpires() == null ? java.sql.Date.valueOf(LocalDate.now().plusDays(membership.getDays().longValue())) :
        java.sql.Date.valueOf((membershipSubscription.getSubscriptionExpires().toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()).plusDays(membership.getDays().longValue())));
    membershipSubscriptionRepository.save(membershipSubscription);
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
