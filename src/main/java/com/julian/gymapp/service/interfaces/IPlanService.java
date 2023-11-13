package com.julian.gymapp.service.interfaces;

import com.julian.gymapp.domain.Plan;
import java.util.List;

public interface IPlanService extends IBasicCrud<Plan, Long>{
  List<Plan> findByMemberId(Long personId);

  Plan findActivePlan(Long memberId);

  Plan activatePlan(Long id);
}
