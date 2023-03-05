package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByMemberNumber(String memberNumber);
  Optional<Member> findMemberByMemberNumber(String memberNumber);
}
