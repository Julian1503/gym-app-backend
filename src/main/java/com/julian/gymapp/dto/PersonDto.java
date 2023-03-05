package com.julian.gymapp.dto;

import com.julian.gymapp.domain.enums.Gender;
import com.julian.gymapp.domain.enums.IdentifierType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;

@Data
public abstract class PersonDto {
  @Size(max = 50, message = "name must be between 0 and 50 characters")
  @NotBlank(message = "name can not be blank")
  @NotNull(message = "name can not be null")
  private String name;
  @Size(max = 50, message = "last name must be between 0 and 50 characters")
  @NotBlank(message = "last name can not be blank")
  @NotNull(message = "last name can not be null")
  private String lastName;
  @Size(max = 9, min = 8, message = "identifier must be between 0 and 9 characters")
  @NotBlank(message = "identifier can not be blank")
  @NotNull(message = "identifier can not be null")
  private String identifier;
  private IdentifierType identifierType;
  @Size(max=15, message = "identifier must be between 0 and 9 characters")
  private String phoneNumber;
  private Byte[] photo;
  @Size(max = 50, message = "street must be between 0 and 50 characters")
  private String street;
  @Size(max = 10, message = "house number must be between 0 and 10 characters")
  private String houseNumber;
  @Size(max = 4, message = "floor must be between 0 and 4 characters")
  private String floor;
  @Size(max = 2, message = "door must be between 0 and 2 characters")
  private String door;
  @NotNull(message = "gender can not be null")
  private Gender gender;
  @NotNull(message = "birth date can not be null")
  @Past(message = "birth date can not be a future or present date")
  private Date birthDate;
}
