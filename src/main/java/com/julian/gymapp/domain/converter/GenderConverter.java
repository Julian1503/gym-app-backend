package com.julian.gymapp.domain.converter;

import static com.julian.gymapp.helper.EnumHelper.fromString;

import com.julian.gymapp.domain.enums.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String> {
  @Override
  public String convertToDatabaseColumn(Gender gender) {
    return gender.toString();
  }

  @Override
  public Gender convertToEntityAttribute(String dbData) {
    return fromString(dbData, Gender.class);
  }
}