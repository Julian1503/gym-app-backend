package com.julian.gymapp.service;

import com.julian.gymapp.domain.InvoiceItem;
import com.julian.gymapp.repository.InvoiceItemRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceItemService implements IBasicCrud<InvoiceItem, Long> {

  private final InvoiceItemRepository invoiceItemRepository;
  private final ModelMapper mapper;

  public InvoiceItemService(InvoiceItemRepository invoiceItemRepository,ModelConfig mapper) {
    this.invoiceItemRepository = invoiceItemRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<InvoiceItem> findAll() {
    return invoiceItemRepository.findAllByIsDeletedFalse();
  }

  @Override
  public InvoiceItem findById(Long id) {
    Optional<InvoiceItem> dayPlan = invoiceItemRepository.findByInvoiceItemIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  @Transactional
  public InvoiceItem save(InvoiceItem entity) {
    validateInvoiceItem(entity);
    return invoiceItemRepository.save(entity);
  }

  @Override
  public InvoiceItem update(InvoiceItem entity, Long id) {
    InvoiceItem invoiceItem = invoiceItemRepository.findByInvoiceItemIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("InvoiceItem was not found"));
    validateInvoiceItem(invoiceItem);
    invoiceItem.setDescription(entity.getDescription());
    invoiceItem.setInvoice(entity.getInvoice());
    invoiceItem.setQuantity(entity.getQuantity());
    invoiceItem.setUnitPrice(entity.getUnitPrice());
    invoiceItem.setSubtotal(entity.getSubtotal());
    return invoiceItemRepository.save(invoiceItem);
  }

  protected void validateInvoiceItem(InvoiceItem entity) {
    if (entity.getDescription() == null || entity.getDescription().isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    }
    if (entity.getInvoice() == null) {
      throw new IllegalArgumentException("Invoice cannot be null");
    }
    if (entity.getQuantity() < 0) {
      throw new IllegalArgumentException("Quantity cannot be null");
    }
    if (entity.getUnitPrice() == null) {
      throw new IllegalArgumentException("UnitPrice cannot be null");
    }
    if (entity.getSubtotal() == null) {
      throw new IllegalArgumentException("Subtotal cannot be null");
    }
  }
  @Override
  public void deleteById(Long id) {
    Optional<InvoiceItem> invoiceItem = invoiceItemRepository.findByInvoiceItemIdAndIsDeletedFalse(id);
    if(invoiceItem.isPresent()) {
      InvoiceItem invoiceItemToDelete = invoiceItem.get();
      invoiceItemToDelete.setDeleted(true);
      invoiceItemRepository.save(invoiceItemToDelete);
    } else {
      throw new IllegalArgumentException("InvoiceItem was not found");
    }
  }
}