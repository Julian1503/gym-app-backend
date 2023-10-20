package com.julian.gymapp.service;

import com.julian.gymapp.domain.Trainer;
import com.julian.gymapp.repository.TrainerRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
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
    return trainerRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Trainer findById(Long id) {
    Optional<Trainer> trainer = trainerRepository.findByPersonIdAndIsDeletedFalse(id);
    return trainer.orElse(null);
  }

  @Override
  public Trainer save(Trainer entity) {
    validateTrainer(entity, false);
    entity.setPhoto("https://fhinstitute.com/wp-content/uploads/2022/01/MASTER-TRAINER.jpg");
    return trainerRepository.save(entity);
  }

  public Trainer createMemberFromPerson(Long personId, Trainer trainer) {
    trainer.setPersonId(personId);
    return trainerRepository.save(trainer);
  }

  @Override
  public Trainer update(Trainer entity, Long id) {
    Trainer trainer = trainerRepository.findByPersonIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(
        TRAINER_NOT_FOUND));
    update(entity, trainer);
    trainer.setSpecialties(entity.getSpecialties());
    validateTrainer(trainer, true);
    return trainerRepository.save(trainer);
  }

  @Override
  public void deleteById(Long id) {
    Optional<Trainer> trainer = trainerRepository.findByPersonIdAndIsDeletedFalse(id);
    if(trainer.isPresent()) {
      Trainer trainerEntity = trainer.get();
      trainerEntity.setDeleted(true);
      trainerRepository.save(trainerEntity);
    } else {
      throw new IllegalArgumentException(TRAINER_NOT_FOUND);
    }
  }

  private void validateTrainer(Trainer entity, boolean isUpdate) {
    validatePerson(entity, isUpdate);

    if (StringUtils.isBlank(entity.getTrainerNumber())) {
      throw new IllegalArgumentException("trainer number cannot be blank");
    }

    if(!isUpdate && trainerRepository.existsByTrainerNumber(entity.getTrainerNumber()) && entity.getPersonId() == null) {
      throw new IllegalArgumentException("trainer number must be unique");
    }

    if (entity.getHireDate() == null) {
      throw new IllegalArgumentException("Hired date cannot be null");
    }
  }

}