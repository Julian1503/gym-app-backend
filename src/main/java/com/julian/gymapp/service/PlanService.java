package com.julian.gymapp.service;

import com.julian.gymapp.domain.Plan;
import com.julian.gymapp.repository.PlanRepository;
import com.julian.gymapp.service.interfaces.IPlanService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanService implements IPlanService {

  private final PlanRepository planRepository;

  public PlanService(PlanRepository planRepository) {
    this.planRepository = planRepository;
  }

  @Override
  public List<Plan> findAll() {
    return planRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Plan findById(Long id) {
    Optional<Plan> plan = planRepository.findByPlanIdAndIsDeletedFalse(id);
    return plan.orElse(null);
  }

  @Override
  @Transactional
  @Modifying
  public Plan save(Plan entity) {
    validatePlan(entity);
    planRepository.updateIsActiveByMemberId(false, entity.getMemberId());
    entity.setActive(true);
    return planRepository.save(entity);
  }

  @Override
  @Transactional
  @Modifying
  public Plan update(Plan entity, Long id) {
    Plan plan = planRepository.findByPlanIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Plan was not found"));
    validatePlan(plan);
    plan.setDayPlans(entity.getDayPlans());
    plan.setName(entity.getName());
    plan.setMemberId(entity.getMemberId());
    plan.setTrainers(entity.getTrainers());
    return planRepository.save(plan);
  }

  protected void validatePlan(Plan entity) {
    if(entity.getName().isBlank() || entity.getName().isEmpty()) {
      throw new IllegalArgumentException("Plan needs a name");
    }

    if(entity.getMemberId() == null) {
      throw new IllegalArgumentException("Plan needs a member to be created");
    }
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    Optional<Plan> plan = planRepository.findByPlanIdAndIsDeletedFalse(id);
    if(plan.isPresent()) {
      Plan planToDelete = plan.get();
      planToDelete.setDeleted(true);
      planRepository.save(planToDelete);
    } else {
      throw new IllegalArgumentException("Plan was not found");
    }
  }

  @Override
  public List<Plan> findByMemberId(Long personId) {
    return planRepository.findByMemberIdAndIsDeletedFalse(personId);
  }

  @Override
  public Plan findActivePlan(Long memberId) {
    return planRepository.findByIsActiveTrueAndIsDeletedFalseAndMemberId(memberId).orElse(null);
  }

  @Override
  public Plan activatePlan(Long id) {
    Plan plan = planRepository.findByPlanIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Plan was not found"));
    planRepository.updateIsActiveByMemberId(false, plan.getMemberId());
    plan.setActive(true);
    return planRepository.save(plan);
  }
}