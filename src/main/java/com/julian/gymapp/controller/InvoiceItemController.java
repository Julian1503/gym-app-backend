package com.julian.gymapp.controller;

import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.domain.InvoiceItem;
import com.julian.gymapp.dto.InvoiceItemDto;
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
@RequestMapping("/api/invoice-item")
public class InvoiceItemController  extends BaseController {

  private final IBasicCrud<InvoiceItem, Long> invoiceItemRepository;
  private final ModelMapper mapper;

  public InvoiceItemController(IBasicCrud<InvoiceItem, Long> invoiceItemRepository, ModelConfig mapper) {
    this.invoiceItemRepository = invoiceItemRepository;
    this.mapper = mapper.getModelMapper();
  }

  @GetMapping("/get-all")
  public ResponseEntity<BaseResponse> getAllInvoiceItems() {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();
      baseResponse = createSuccessResponse(convertToDto(invoiceItems), "InvoiceItems returned successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      InvoiceItem invoiceItem = invoiceItemRepository.findById(id);
      if (invoiceItem == null) {
        baseResponse = createErrorResponse("InvoiceItem was not found");
      } else {
        baseResponse = createSuccessResponse(convertToDto(invoiceItem), "InvoiceItem returned successfully");
      }
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PostMapping("/create")
  public ResponseEntity<BaseResponse> createInvoiceItem(@Valid @RequestBody InvoiceItemDto invoiceItemDto) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      InvoiceItem invoiceItem = mapper.map(invoiceItemDto, InvoiceItem.class);
      InvoiceItem savedInvoiceItem = invoiceItemRepository.save(invoiceItem);
      baseResponse = createSuccessResponse(convertToDto(savedInvoiceItem), "InvoiceItem was created successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<BaseResponse> updateInvoiceItem(@Valid @RequestBody InvoiceItemDto invoiceItemDto, @PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      InvoiceItem invoiceItem = mapper.map(invoiceItemDto, InvoiceItem.class);
      InvoiceItem updatedInvoiceItem = invoiceItemRepository.update(invoiceItem, id);
      baseResponse = createSuccessResponse(convertToDto(updatedInvoiceItem), "InvoiceItem was updated successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<BaseResponse> deleteInvoiceItem(@PathVariable Long id) {
    ResponseEntity<BaseResponse> baseResponse;
    try {
      invoiceItemRepository.deleteById(id);
      baseResponse = createSuccessResponse(null, "InvoiceItem was deleted successfully");
    } catch (Exception e) {
      baseResponse = createErrorResponse(e.getMessage());
    }
    return baseResponse;
  }

  private InvoiceItemDto convertToDto(InvoiceItem invoiceItem) {
    return mapper.map(invoiceItem, InvoiceItemDto.class);
  }

  private List<InvoiceItemDto> convertToDto(List<InvoiceItem> invoiceItemList) {
    return invoiceItemList.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}