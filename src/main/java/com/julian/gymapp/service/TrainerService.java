package com.julian.gymapp.service;

import com.julian.gymapp.domain.Trainer;
import com.julian.gymapp.repository.TrainerRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TrainerService extends PersonService implements IBasicCrud<Trainer, Long> {

  public static final String TRAINER_NOT_FOUND = "Trainer not found";
  private final TrainerRepository trainerRepository;

  public TrainerService(TrainerRepository trainerRepository) {
    this.trainerRepository = trainerRepository;
  }
  @Override
  public List<Trainer> findAll() {
    return trainerRepository.findAll();
  }

  @Override
  public Trainer findById(Long id) {
    Optional<Trainer> trainer = trainerRepository.findById(id);
    return trainer.orElse(null);
  }

  @Override
  public Trainer save(Trainer entity) {
    validateTrainer(entity);
    return trainerRepository.save(entity);
  }

  public Trainer createMemberFromPerson(Long personId, Trainer trainer) {
    trainer.setPersonId(personId);
    return trainerRepository.save(trainer);
  }

  @Override
  public Trainer update(Trainer entity, Long id) {
    Trainer trainer = trainerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
        TRAINER_NOT_FOUND));
    update(entity, trainer);
    validateTrainer(trainer);
    return trainerRepository.save(trainer);
  }

  @Override
  public void delete(Trainer trainerToDelete) {
    if (StringUtils.isBlank(trainerToDelete.getTrainerNumber())) {
      throw new IllegalArgumentException("trainer number cannot be blank");
    }
   Optional<Trainer> trainer = trainerRepository.findTrainerByTrainerNumber(trainerToDelete.getTrainerNumber());
   if(trainer.isPresent()) {
     trainerRepository.delete(trainer.get());
   } else {
     throw new IllegalArgumentException(TRAINER_NOT_FOUND);
   }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Trainer> trainer = trainerRepository.findById(id);
    if(trainer.isPresent()) {
      trainerRepository.deleteById(id);
    } else {
      throw new IllegalArgumentException(TRAINER_NOT_FOUND);
    }
  }

  private void validateTrainer(Trainer entity) {
    validatePerson(entity);

    if (StringUtils.isBlank(entity.getTrainerNumber())) {
      throw new IllegalArgumentException("trainer number cannot be blank");
    }

    if(trainerRepository.existsByTrainerNumber(entity.getTrainerNumber()) && entity.getPersonId() == null) {
      throw new IllegalArgumentException("trainer number must be unique");
    }

    if (entity.getHireDate() == null) {
      throw new IllegalArgumentException("Hired date cannot be null");
    }
  }

}