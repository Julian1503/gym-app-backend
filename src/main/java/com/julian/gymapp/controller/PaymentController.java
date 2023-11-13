package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Payment;
import com.julian.gymapp.dto.PaymentDto;
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
@RequestMapping("/api/payment")
public class PaymentController extends BaseController {

  private final IBasicCrud<Payment, Long> invoiceRepository;
  private final ModelMapper mapper;

  public PaymentController(IBasicCrud<Payment, Long> invoiceRepository, ModelConfig mapper) {
    this.invoiceRepository = invoiceRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllPayments() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Payment> invoices = invoiceRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(invoices), "Payments returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Payment invoice = invoiceRepository.findById(id);
      if (invoice == null) {
        baseResponse = createErrorResponse("Payment was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(invoice), "Payment returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createPayment(@Valid @RequestBody PaymentDto invoiceDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Payment invoice = mapper.map(invoiceDto, Payment.class);
      Payment savedPayment = invoiceRepository.save(invoice);
      baseResponse = createSuccessResponse(convertToDto(savedPayment), "Payment was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updatePayment(@Valid @RequestBody PaymentDto invoiceDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Payment invoice = mapper.map(invoiceDto, Payment.class);
      Payment updatedPayment = invoiceRepository.update(invoice, id);
      baseResponse = createSuccessResponse(convertToDto(updatedPayment), "Payment was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deletePayment(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      invoiceRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Payment was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private PaymentDto convertToDto(Payment invoice) {
    return mapper.map(invoice, PaymentDto.class);
  }

  private List<PaymentDto> convertToDto(List<Payment> invoiceList) {
    return invoiceList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}