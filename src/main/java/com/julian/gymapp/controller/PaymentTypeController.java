package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.PaymentType;
import com.julian.gymapp.dto.PaymentTypeDto;
import com.julian.gymapp.service.interfaces.IBasicCrud;
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
@RequestMapping("/api/payment-type")
public class PaymentTypeController extends BaseController {

  private final IBasicCrud<PaymentType, Long> paymentTypeRepository;
  private final ModelMapper mapper;

  public PaymentTypeController(IBasicCrud<PaymentType, Long> paymentTypeRepository, ModelConfig mapper) {
    this.paymentTypeRepository = paymentTypeRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllPaymentTypes() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<PaymentType> paymentTypes = paymentTypeRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(paymentTypes), "PaymentTypes returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      PaymentType paymentType = paymentTypeRepository.findById(id);
      if (paymentType == null) {
        baseResponse = createErrorResponse("PaymentType was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(paymentType), "PaymentType returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createPaymentType(@Valid @RequestBody PaymentTypeDto paymentTypeDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      PaymentType paymentType = mapper.map(paymentTypeDto, PaymentType.class);
      PaymentType savedPaymentType = paymentTypeRepository.save(paymentType);
      baseResponse = createSuccessResponse(convertToDto(savedPaymentType), "PaymentType was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updatePaymentType(@Valid @RequestBody PaymentTypeDto paymentTypeDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      PaymentType paymentType = mapper.map(paymentTypeDto, PaymentType.class);
      PaymentType updatedPaymentType = paymentTypeRepository.update(paymentType, id);
      baseResponse = createSuccessResponse(convertToDto(updatedPaymentType), "PaymentType was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deletePaymentType(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      paymentTypeRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "PaymentType was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private PaymentTypeDto convertToDto(PaymentType paymentType) {
    return mapper.map(paymentType, PaymentTypeDto.class);
  }

  private List<PaymentTypeDto> convertToDto(List<PaymentType> paymentTypeList) {
    return paymentTypeList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}