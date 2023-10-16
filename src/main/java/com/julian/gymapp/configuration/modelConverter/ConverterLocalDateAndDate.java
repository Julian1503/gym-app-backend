package com.julian.gymapp.configuration.modelConverter;

import java.time.LocalDate;
import java.util.Date;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ConverterLocalDateAndDate implements Converter<LocalDate, Date> {

  @Override
  public Date convert(MappingContext<LocalDate, Date> mappingContext) {
    LocalDate source = mappingContext.getSource();
    try {
      if(source == null) return null;
      return java.sql.Date.valueOf(source);
    } catch (NumberFormatException e) {
      System.out.println("Error converting date: " + e.getMessage());
    }
    return null;
  }
}
