package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Equipment;
import com.julian.gymapp.dto.EquipmentDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/equipment")
public class EquipmentController extends BaseController {

  private final IBasicCrud<Equipment, Long> equipmentRepository;
  private final ModelMapper mapper;

  public EquipmentController(IBasicCrud<Equipment, Long> equipmentRepository, ModelConfig mapper) {
    this.equipmentRepository = equipmentRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllEquipments() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Equipment> equipments = equipmentRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(equipments), "Equipments returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Equipment equipment = equipmentRepository.findById(id);
      if (equipment == null) {
        baseResponse = createErrorResponse("Equipment was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(equipment), "Equipment returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createEquipment(@Valid @RequestBody EquipmentDto equipmentDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Equipment equipment = mapper.map(equipmentDto, Equipment.class);
      Equipment savedEquipment = equipmentRepository.save(equipment);
      baseResponse = createSuccessResponse(convertToDto(savedEquipment), "Equipment was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateEquipment(@Valid @RequestBody EquipmentDto equipmentDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Equipment equipment = mapper.map(equipmentDto, Equipment.class);
      Equipment updatedEquipment = equipmentRepository.update(equipment, id);
      baseResponse = createSuccessResponse(convertToDto(updatedEquipment), "Equipment was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteEquipment(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      equipmentRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Equipment was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private EquipmentDto convertToDto(Equipment equipment) {
    return mapper.map(equipment, EquipmentDto.class);
  }

  private List<EquipmentDto> convertToDto(List<Equipment> equipmentList) {
    return equipmentList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
