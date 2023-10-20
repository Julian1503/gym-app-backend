package com.julian.gymapp.repository;

import com.julian.gymapp.domain.MembershipSubscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembershipSubscriptionRepository extends JpaRepository<MembershipSubscription, Long> {
  List<MembershipSubscription> findAllByIsDeletedFalse();
  Optional<MembershipSubscription> findByMembershipSubscriptionIdAndIsDeletedFalse(Long id);

  @Query("SELECT ms FROM MembershipSubscription ms WHERE ms.memberId = ?1 AND ms.isDeleted = false ORDER BY ms.subscriptionStart DESC")
  Optional<MembershipSubscription> findLatestMembershipSubscriptionByMemberIdAndIsDeletedFalse(Long memberId);
}
