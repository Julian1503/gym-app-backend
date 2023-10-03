package com.julian.gymapp.service;

import com.julian.gymapp.domain.Specialty;
import com.julian.gymapp.repository.SpecialtyRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyService implements IBasicCrud<Specialty, Long> {

  private final SpecialtyRepository specialtyRepository;

  public SpecialtyService(SpecialtyRepository specialtyRepository) {
    this.specialtyRepository = specialtyRepository;
  }

  @Override
  public List<Specialty> findAll() {
    return specialtyRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Specialty findById(Long id) {
    Optional<Specialty> specialty = specialtyRepository.findBySpecialtyIdAndIsDeletedFalse(id);
    return specialty.orElse(null);
  }

  @Override
  public Specialty save(Specialty entity) {
    validateSpecialty(entity);
    return specialtyRepository.save(entity);
  }

  @Override
  public Specialty update(Specialty entity, Long id) {
    Specialty specialty = specialtyRepository.findBySpecialtyIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Specialty was not found"));
    validateSpecialty(specialty);
    specialty.setDescription(entity.getDescription());
    specialty.setName(entity.getName());
    specialty.setPhoto(entity.getPhoto());
    return specialtyRepository.save(specialty);
  }

  protected void validateSpecialty(Specialty entity) {
    if(entity.getName().isBlank() || entity.getName().isEmpty()) {
      throw new IllegalArgumentException("Specialty needs a name");
    }

    if(entity.getDescription().isBlank() || entity.getDescription().isEmpty()) {
      throw new IllegalArgumentException("Specialty needs a description");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Specialty> specialty = specialtyRepository.findBySpecialtyIdAndIsDeletedFalse(id);
    if(specialty.isPresent()) {
      Specialty specialtyToDelete = specialty.get();
      specialtyToDelete.setDeleted(true);
      specialtyRepository.save(specialtyToDelete);
    } else {
      throw new IllegalArgumentException("Specialty was not found");
    }
  }
}