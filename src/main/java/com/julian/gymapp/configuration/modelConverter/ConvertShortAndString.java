package com.julian.gymapp.configuration.modelConverter;

import java.time.LocalDate;
import java.util.Date;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ConvertShortAndString implements Converter<Short, String> {

  @Override
  public String convert(MappingContext<Short, String> mappingContext) {
    Short source = mappingContext.getSource();
    try {
      if(source == null) return null;
      return transformShortToTime(source);
    } catch (NumberFormatException e) {
      System.out.println("Error converting date: " + e.getMessage());
    }
    return null;
  }

  private String transformShortToTime(Short time){
    if(time == null) {
      return null;
    }
    Short minutes = (short) (time / 60);
    Short seconds = (short) (time % 60);
    return minutes + ":" + seconds;
  }
}
