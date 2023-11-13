package com.julian.gymapp.service;

import com.julian.gymapp.domain.MembershipSubscription;
import com.julian.gymapp.repository.MembershipSubscriptionRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MembershipSubscriptionService  implements IBasicCrud<MembershipSubscription, Long> {

  private final MembershipSubscriptionRepository membershipSubscriptionRepository;

  public MembershipSubscriptionService(MembershipSubscriptionRepository membershipSubscriptionRepository) {
    this.membershipSubscriptionRepository = membershipSubscriptionRepository;
  }

  @Override
  public List<MembershipSubscription> findAll() {
    return membershipSubscriptionRepository.findAllByIsDeletedFalse();
  }

  @Override
  public MembershipSubscription findById(Long id) {
    Optional<MembershipSubscription> membershipSubscription = membershipSubscriptionRepository.findByMembershipSubscriptionIdAndIsDeletedFalse(id);
    return membershipSubscription.orElse(null);
  }

  @Override
  public MembershipSubscription save(MembershipSubscription entity) {
    validateMembership(entity);
    return membershipSubscriptionRepository.save(entity);
  }

  @Override
  public MembershipSubscription update(MembershipSubscription entity, Long id) {
    MembershipSubscription membershipSubscription = membershipSubscriptionRepository.findByMembershipSubscriptionIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("MembershipSubscription was not found"));
    validateMembership(membershipSubscription);
    membershipSubscription.setMembershipId(entity.getMembershipId());
    membershipSubscription.setSubscriptionExpires(entity.getSubscriptionExpires());
    membershipSubscription.setSubscriptionStart(entity.getSubscriptionStart());
    membershipSubscription.setAmount(entity.getAmount());
    membershipSubscription.setDiscount(membershipSubscription.getDiscount());
    membershipSubscription.setExpired(entity.isExpired());
    membershipSubscription.setMemberId(entity.getMemberId());
    return membershipSubscriptionRepository.save(membershipSubscription);
  }

  protected void validateMembership(MembershipSubscription entity) {
    if(entity.getAmount() <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }

    if(entity.getSubscriptionExpires().before(entity.getSubscriptionStart())) {
      throw new IllegalArgumentException("The expires date must be greater than the start date");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<MembershipSubscription> membershipSubscription = membershipSubscriptionRepository.
        findByMembershipSubscriptionIdAndIsDeletedFalse(id);
    if(membershipSubscription.isPresent()) {
      MembershipSubscription membershipSubscriptionToDelete = membershipSubscription.get();
      membershipSubscriptionToDelete.setDeleted(true);
      membershipSubscriptionRepository.save(membershipSubscriptionToDelete);
    } else {
      throw new IllegalArgumentException("MembershipSubscription was not found");
    }
  }
}