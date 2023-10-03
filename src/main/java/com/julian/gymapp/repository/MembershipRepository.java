package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Membership;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
  List<Membership> findAllByIsDeletedFalse();
  Optional<Membership> findByMembershipIdAndIsDeletedFalse(Long id);
}
