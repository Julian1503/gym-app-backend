package com.julian.gymapp.repository;

import com.julian.gymapp.domain.Equipment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
  List<Equipment> findAllByIsDeletedFalse();
  Optional<Equipment> findByEquipmentIdAndIsDeletedFalse(Long id);
}
