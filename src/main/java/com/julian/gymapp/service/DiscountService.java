package com.julian.gymapp.service;

import com.julian.gymapp.domain.Discount;
import com.julian.gymapp.repository.DiscountRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public class DiscountService implements IBasicCrud<Discount, Long> {

  private final DiscountRepository discountRepository;

  public DiscountService(DiscountRepository discountRepository) {
    this.discountRepository = discountRepository;
  }

  @Override
  public List<Discount> findAll() {
    return discountRepository.findAll();
  }

  @Override
  public Discount findById(Long id) {
    Optional<Discount> dayPlan = discountRepository.findById(id);
    return dayPlan.orElse(null);
  }

  @Override
  public Discount save(Discount entity) {
    validateDiscount(entity);
    return discountRepository.save(entity);
  }

  @Override
  public Discount update(Discount entity, Long id) {
    Discount dayPlan = discountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Day plan was not found"));
    validateDiscount(dayPlan);
    return discountRepository.save(dayPlan);
  }

  protected void validateDiscount(Discount entity) {
    if(entity.getCode().isEmpty() || entity.getCode().isBlank()) {
      throw new IllegalArgumentException("The code is required");
    }

    if(entity.getPercentage().isNaN() || entity.getPercentage() <= 0) {
      throw new IllegalArgumentException("The percentage must major to 0");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Discount> dayPlan = discountRepository.findById(id);
    if(dayPlan.isPresent()) {
      discountRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException("Day plan was not found");
    }
  }
}