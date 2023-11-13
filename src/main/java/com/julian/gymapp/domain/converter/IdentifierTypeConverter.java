package com.julian.gymapp.domain.converter;

import static com.julian.gymapp.helper.EnumHelper.fromString;

import com.julian.gymapp.domain.enums.IdentifierType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IdentifierTypeConverter implements AttributeConverter<IdentifierType, String> {
  @Override
  public String convertToDatabaseColumn(IdentifierType identifierType) {
    return identifierType.getDescription();
  }

  @Override
  public IdentifierType convertToEntityAttribute(String dbData) {
    return fromString(dbData, IdentifierType.class);
  }
}