package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
