package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
  boolean existsByMemberNumber(String memberNumber);
  Optional<Member> findMemberByMemberNumber(String memberNumber);
  List<Member> findAllByIsDeletedFalse();
  Optional<Member> findByPersonIdAndIsDeletedFalse(Long id);
  @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.user.personId = ?1")
  boolean hasUser(Long personId);
}
