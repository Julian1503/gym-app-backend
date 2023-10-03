package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Specialty;
import com.julian.gymapp.dto.SpecialtyDto;
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
@RequestMapping("/api/specialty")
public class SpecialtyController extends BaseController {

  private final IBasicCrud<Specialty, Long> specialtyRepository;
  private final ModelMapper mapper;

  public SpecialtyController(IBasicCrud<Specialty, Long> specialtyRepository, ModelConfig mapper) {
    this.mapper = mapper.getModelMapper();
    this.specialtyRepository = specialtyRepository;
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllSpecialties() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Specialty> specialties = specialtyRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(specialties), "Specialties returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Specialty specialty = specialtyRepository.findById(id);
      if (specialty == null) {
        baseResponse = createErrorResponse("Specialty was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(specialty), "Specialty returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Specialty specialty = mapper.map(specialtyDto, Specialty.class);
      Specialty savedSpecialty = specialtyRepository.save(specialty);
      baseResponse = createSuccessResponse(convertToDto(savedSpecialty), "Specialty was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Specialty specialty = mapper.map(specialtyDto, Specialty.class);
      Specialty updatedSpecialty = specialtyRepository.update(specialty, id);
      baseResponse = createSuccessResponse(convertToDto(updatedSpecialty), "Specialty was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteSpecialty(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      specialtyRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Specialty was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private SpecialtyDto convertToDto(Specialty specialty) {
    return mapper.map(specialty, SpecialtyDto.class);
  }

  private List<SpecialtyDto> convertToDto(List<Specialty> specialties) {
    return specialties.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
