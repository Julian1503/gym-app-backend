package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Plan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
  List<Plan> findAllByIsDeletedFalse();
  Optional<Plan> findByPlanIdAndIsDeletedFalse(Long id);

  List<Plan> findByMemberIdAndIsDeletedFalse(Long memberId);

  Optional<Plan> findByIsActiveTrueAndIsDeletedFalseAndMemberId(Long memberId);

  @Transactional
  @Modifying
  @Query("UPDATE Plan p SET p.isActive = :isActive WHERE p.memberId = :memberId")
  void updateIsActiveByMemberId(Boolean isActive, Long memberId);
}
