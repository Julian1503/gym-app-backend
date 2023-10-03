package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpecialtyDto {
  private Long specialtyId;
  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name can have a maximum of 100 characters")
  private String name;
  @Size(max = 500, message = "Description can have a maximum of 500 characters")
  private String description;
  private Byte[] photo;
}