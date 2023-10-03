package com.julian.gymapp.service;

import com.julian.gymapp.domain.Equipment;
import com.julian.gymapp.repository.EquipmentRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService implements IBasicCrud<Equipment, Long> {

  private final EquipmentRepository equipmentRepository;

  public EquipmentService(EquipmentRepository equipmentRepository) {
    this.equipmentRepository = equipmentRepository;
  }

  @Override
  public List<Equipment> findAll() {
    return equipmentRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Equipment findById(Long id) {
    Optional<Equipment> equipment = equipmentRepository.findByEquipmentIdAndIsDeletedFalse(id);
    return equipment.orElse(null);
  }

  @Override
  public Equipment save(Equipment entity) {
    validateEquipment(entity);
    return equipmentRepository.save(entity);
  }

  @Override
  public Equipment update(Equipment entity, Long id) {
    Equipment equipment = equipmentRepository.findByEquipmentIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Equipment was not found"));
    validateEquipment(equipment);
    equipment.setName(entity.getName());
    equipment.setQuantity(entity.getQuantity());
    equipment.setType(entity.getType());
    return equipmentRepository.save(equipment);
  }

  protected void validateEquipment(Equipment entity) {
    if(entity.getName().isBlank() || entity.getName().isEmpty()) {
      throw new IllegalArgumentException("Name can not be blank or empty");
    }
    
    if(entity.getType() == null) {
      throw new IllegalArgumentException("The equipment must have a type");
    }
  }

  @Override
  public void deleteById(Long id) {
    Optional<Equipment> equipment = equipmentRepository.findByEquipmentIdAndIsDeletedFalse(id);
    if(equipment.isPresent()) {
      Equipment equipmentToDelete = equipment.get();
      equipmentToDelete.setDeleted(true);
      equipmentRepository.save(equipmentToDelete);
    } else {
      throw new IllegalArgumentException("Equipment was not found");
    }
  }
}