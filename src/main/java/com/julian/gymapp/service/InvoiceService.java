package com.julian.gymapp.service;

import com.julian.gymapp.domain.Invoice;
import com.julian.gymapp.repository.InvoiceRepository;
import com.julian.gymapp.service.interfaces.IBasicCrud;
import com.julian.gymapp.service.interfaces.ModelConfig;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService implements IBasicCrud<Invoice, Long> {

  private final InvoiceRepository invoiceRepository;
  private final ModelMapper mapper;

  public InvoiceService(InvoiceRepository invoiceRepository, ModelConfig mapper) {
    this.invoiceRepository = invoiceRepository;
    this.mapper = mapper.getModelMapper();
  }

  @Override
  public List<Invoice> findAll() {
    return invoiceRepository.findAllByIsDeletedFalse();
  }

  @Override
  public Invoice findById(Long id) {
    Optional<Invoice> dayPlan = invoiceRepository.findByInvoiceIdAndIsDeletedFalse(id);
    return dayPlan.orElse(null);
  }

  @Override
  @Transactional
  public Invoice save(Invoice entity) {
    validateInvoice(entity);
    return invoiceRepository.save(entity);
  }

  @Override
  public Invoice update(Invoice entity, Long id) {
    Invoice invoice = invoiceRepository.findByInvoiceIdAndIsDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException("Invoice was not found"));
    validateInvoice(invoice);
    invoice.setInvoiceDate(entity.getInvoiceDate());
    invoice.setInvoiceNumber(entity.getInvoiceNumber());
    invoice.setItems(entity.getItems());
    invoice.setTaxRate(entity.getTaxRate());
    invoice.setTotalAmount(entity.getTotalAmount());
    return invoiceRepository.save(invoice);
  }

  protected void validateInvoice(Invoice entity) {

  }

  @Override
  public void deleteById(Long id) {
    Optional<Invoice> invoice = invoiceRepository.findByInvoiceIdAndIsDeletedFalse(id);
    if(invoice.isPresent()) {
      Invoice invoiceToDelete = invoice.get();
      invoiceToDelete.setDeleted(true);
      invoiceRepository.save(invoiceToDelete);
    } else {
      throw new IllegalArgumentException("Invoice was not found");
    }
  }
}
