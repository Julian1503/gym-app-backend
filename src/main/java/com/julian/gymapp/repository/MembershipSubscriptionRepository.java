package com.julian.gymapp.repository;

import com.julian.gymapp.domain.MembershipSubscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipSubscriptionRepository extends JpaRepository<MembershipSubscription, Long> {
  List<MembershipSubscription> findAllByIsDeletedFalse();
  Optional<MembershipSubscription> findByMembershipSubscriptionIdAndIsDeletedFalse(Long id);
}
