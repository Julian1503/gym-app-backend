package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.Invoice;
import com.julian.gymapp.dto.InvoiceDto;
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
@RequestMapping("/api/invoice")
public class InvoiceController extends BaseController {

  private final IBasicCrud<Invoice, Long> invoiceRepository;
  private final ModelMapper mapper;

  public InvoiceController(IBasicCrud<Invoice, Long> invoiceRepository, ModelConfig mapper) {
    this.invoiceRepository = invoiceRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllInvoices() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<Invoice> invoices = invoiceRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(invoices), "Invoices returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Invoice invoice = invoiceRepository.findById(id);
      if (invoice == null) {
        baseResponse = createErrorResponse("Invoice was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(invoice), "Invoice returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Invoice invoice = mapper.map(invoiceDto, Invoice.class);
      Invoice savedInvoice = invoiceRepository.save(invoice);
      baseResponse = createSuccessResponse(convertToDto(savedInvoice), "Invoice was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateInvoice(@Valid @RequestBody InvoiceDto invoiceDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      Invoice invoice = mapper.map(invoiceDto, Invoice.class);
      Invoice updatedInvoice = invoiceRepository.update(invoice, id);
      baseResponse = createSuccessResponse(convertToDto(updatedInvoice), "Invoice was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteInvoice(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      invoiceRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "Invoice was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private InvoiceDto convertToDto(Invoice invoice) {
    return mapper.map(invoice, InvoiceDto.class);
  }

  private List<InvoiceDto> convertToDto(List<Invoice> invoiceList) {
    return invoiceList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}