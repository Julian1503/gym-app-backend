package com.julian.gymapp.configuration.modelConverter;

import org.modelmapper.Converter;
import java.lang.annotation.Annotation;
import org.modelmapper.spi.MappingContext;

public class ConvertStringAndShort implements Converter<String, Short> {

  @Override
  public Short convert(MappingContext<String, Short> mappingContext) {
    String source = mappingContext.getSource();
    try {
      if(source == null) return null;
      return transformTimeToShort(source);
    } catch (NumberFormatException e) {
      System.out.println("Error converting date: " + e.getMessage());
    }
    return null;
  }
  private Short transformTimeToShort(String time){
    if(time == null || time.isEmpty()){
      return null;
    }
    String[] timeSplit = time.split(":");
    Short minutes = Short.parseShort(timeSplit[0]);
    Short seconds = Short.parseShort(timeSplit[1]);
    return (short) ((minutes * 60) + seconds);
  }
}
