package com.julian.gymapp.configuration.modelConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ConverterDateAndLocalDate implements Converter<Date, LocalDate> {
  @Override
  public LocalDate convert(MappingContext<Date, LocalDate> mappingContext) {
    Date source = mappingContext.getSource();
    try {
      Instant instant = source.toInstant();
      return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    } catch (NumberFormatException e) {
      System.out.println("Error converting date: " + e.getMessage());
    }
    return null;
  }

}
