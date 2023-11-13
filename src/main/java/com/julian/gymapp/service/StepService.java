package com.julian.gymapp.service;

import com.julian.gymapp.domain.Step;
import com.julian.gymapp.repository.StepRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StepService  implements IBasicCrud<Step, Long> {

  private final StepRepository stepRepository;

  public StepService(StepRepository stepRepository) {
    this.stepRepository = stepRepository;
  }

  @Override
  public List<Step> findAll() {
    return stepRepository.findAll();
  }

  @Override
  public Step findById(Long id) {
    Optional<Step> dayPlan = stepRepository.findById(id);
    return dayPlan.orElse(null);
  }

  @Override
  public Step save(Step entity) {
    validateStep(entity);
    return stepRepository.save(entity);
  }

  @Override
  public Step update(Step entity, Long id) {
    Step step = stepRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Step was not found"));
    validateStep(step);
    step.setDescription(entity.getDescription());
    step.setOrder(entity.getOrder());
    return stepRepository.save(step);
  }

  protected void validateStep(Step entity) {
    if(entity.getDescription().isBlank() || entity.getDescription().isEmpty()) {
      throw new IllegalArgumentException("Step needs a description");
    }
    if(entity.getOrder() <= 0) {
      throw new IllegalArgumentException("Order must be greater than 0");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Step> dayPlan = stepRepository.findById(id);
    if(dayPlan.isPresent()) {
      stepRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("Step was not found");
    }
  }
}