package com.julian.gymapp.service;

import com.julian.gymapp.domain.Membership;
import com.julian.gymapp.repository.MembershipRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MembershipService  implements IBasicCrud<Membership, Long> {

  private final MembershipRepository membershipRepository;

  public MembershipService(MembershipRepository membershipRepository) {
    this.membershipRepository = membershipRepository;
  }

  @Override
  public List<Membership> findAll() {
    return membershipRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Membership findById(Long id) {
    Optional<Membership> membership = membershipRepository.findByMembershipIdAndIsDeletedFalse(id);
    return membership.orElse(null);
  }

  @Override
  public Membership save(Membership entity) {
    validateMembership(entity);
    return membershipRepository.save(entity);
  }

  @Override
  public Membership update(Membership entity, Long id) {
    Membership membership = membershipRepository.findByMembershipIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Membership was not found"));
    validateMembership(membership);
    membership.setDays(entity.getDays());
    membership.setName(entity.getName());
    membership.setDescription(entity.getDescription());
    membership.setPrice(entity.getPrice());
    return membershipRepository.save(membership);
  }

  protected void validateMembership(Membership entity) {
    if(entity.getName().isBlank() || entity.getName().isEmpty()) {
      throw new IllegalArgumentException("Name must not be blank or empty");
    }

    if(entity.getPrice() <= 0) {
      throw new IllegalArgumentException("Price must be more than 0");
    }

    if(entity.getDescription().isEmpty() || entity.getDescription().isBlank()) {
      throw new IllegalArgumentException("Description must not be blank or empty");
    }

    if(entity.getDays() <= 0) {
      throw new IllegalArgumentException("The days must be more than 0");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Membership> membership = membershipRepository.findByMembershipIdAndIsDeletedFalse(id);
    if(membership.isPresent()) {
      Membership membershipToDelete = membership.get();
      membershipToDelete.setDeleted(true);
      membershipRepository.save(membershipToDelete);
    } else {
      throw new IllegalArgumentException("Membership was not found");
    }
  }
}