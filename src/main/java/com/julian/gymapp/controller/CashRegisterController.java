package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.CashRegister;
import com.julian.gymapp.dto.CashRegisterDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ICashRegisterService;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/api/cash-register")
public class CashRegisterController extends BaseController {

  private final ICashRegisterService cashRegisterRepository;
  private final ModelMapper mapper;

  public CashRegisterController(ICashRegisterService cashRegisterRepository, ModelConfig mapper) {
    this.cashRegisterRepository = cashRegisterRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllCashRegisters() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<CashRegister> cashRegisters = cashRegisterRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(cashRegisters), "CashRegisters returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashRegister cashRegister = cashRegisterRepository.findById(id);
      if (cashRegister == null) {
        baseResponse = createErrorResponse("CashRegister was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(cashRegister), "CashRegister returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/last-registration")
  public ResponseEntity<BaseResponse> getLastRegistration() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashRegister cashRegister = cashRegisterRepository.getLastBalance();
      if (cashRegister == null) {
        baseResponse = createErrorResponse("CashRegister was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(cashRegister), "CashRegister returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }


  @PutMapping("/close/{id}")
  public ResponseEntity<BaseResponse> closeCashRegister(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashRegister cashRegister = cashRegisterRepository.closeCashRegister(id);
      if (cashRegister == null) {
        baseResponse = createErrorResponse("CashRegister was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(cashRegister), "CashRegister returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createCashRegister(@Valid @RequestBody CashRegisterDto cashRegisterDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashRegister cashRegister = mapper.map(cashRegisterDto, CashRegister.class);
      CashRegister savedCashRegister = cashRegisterRepository.save(cashRegister);
      baseResponse = createSuccessResponse(convertToDto(savedCashRegister), "CashRegister was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateCashRegister(@Valid @RequestBody CashRegisterDto cashRegisterDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashRegister cashRegister = mapper.map(cashRegisterDto, CashRegister.class);
      CashRegister updatedCashRegister = cashRegisterRepository.update(cashRegister, id);
      baseResponse = createSuccessResponse(convertToDto(updatedCashRegister), "CashRegister was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteCashRegister(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      cashRegisterRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "CashRegister was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private CashRegisterDto convertToDto(CashRegister cashRegister) {
    return mapper.map(cashRegister, CashRegisterDto.class);
  }

  private List<CashRegisterDto> convertToDto(List<CashRegister> cashRegisterList) {
    return cashRegisterList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}