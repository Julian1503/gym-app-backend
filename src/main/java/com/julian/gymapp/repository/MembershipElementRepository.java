package com.julian.gymapp.repository;

import com.julian.gymapp.domain.MembershipElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipElementRepository extends JpaRepository<MembershipElement, Long> {

}
