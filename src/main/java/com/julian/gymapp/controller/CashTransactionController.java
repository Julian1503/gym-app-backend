package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.CashRegister;
import com.julian.gymapp.domain.CashTransaction;
import com.julian.gymapp.domain.PaymentType;
import com.julian.gymapp.dto.CashTransactionDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ICashTransactionService;
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
@RequestMapping("/api/cash-transaction")
public class CashTransactionController extends BaseController {

  private final ICashTransactionService cashTransactionRepository;
  private final ModelMapper mapper;

  public CashTransactionController(ICashTransactionService cashTransactionRepository, ModelConfig mapper) {
    this.cashTransactionRepository = cashTransactionRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllCashTransactions() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<CashTransaction> cashTransactions = cashTransactionRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(cashTransactions), "CashTransactions returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashTransaction cashTransaction = cashTransactionRepository.findById(id);
      if (cashTransaction == null) {
        baseResponse = createErrorResponse("CashTransaction was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(cashTransaction), "CashTransaction returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get-by-cash-register/{id}")
  public ResponseEntity<BaseResponse> getByCashRegisterId(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<CashTransaction> cashTransactions = cashTransactionRepository.findByCashRegisterId(id);
      if (cashTransactions == null) {
        baseResponse = createErrorResponse("CashTransactions were not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(cashTransactions), "CashTransactions returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createCashTransaction(@Valid @RequestBody CashTransactionDto cashTransactionDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashTransaction cashTransaction = mapper.map(cashTransactionDto, CashTransaction.class);
      cashTransaction.setCashRegister(new CashRegister(cashTransactionDto.getCashRegisterId()));
      cashTransaction.setPaymentType(new PaymentType(cashTransactionDto.getPaymentTypeId()));
      CashTransaction savedCashTransaction = cashTransactionRepository.save(cashTransaction);
      baseResponse = createSuccessResponse(convertToDto(savedCashTransaction), "CashTransaction was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateCashTransaction(@Valid @RequestBody CashTransactionDto cashTransactionDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      CashTransaction cashTransaction = mapper.map(cashTransactionDto, CashTransaction.class);
      CashTransaction updatedCashTransaction = cashTransactionRepository.update(cashTransaction, id);
      baseResponse = createSuccessResponse(convertToDto(updatedCashTransaction), "CashTransaction was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteCashTransaction(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      cashTransactionRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "CashTransaction was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private CashTransactionDto convertToDto(CashTransaction cashTransaction) {
    return mapper.map(cashTransaction, CashTransactionDto.class);
  }

  private List<CashTransactionDto> convertToDto(List<CashTransaction> cashTransactionList) {
    return cashTransactionList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}