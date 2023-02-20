package com.julian.gymapp.repository;

import com.julian.gymapp.domain.DayPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayPlanRepository extends JpaRepository<DayPlan, Long> {

}
