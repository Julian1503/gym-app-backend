package com.julian.gymapp.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MembershipElementDto {
  private Long elementId;

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  private String name;

  @Size(max = 200, message = "Description cannot exceed 200 characters")
  private String description;

  @NotNull(message = "Cost cannot be null")
  @DecimalMin(value = "0.01", message = "Cost must be at least 0.01")
  @DecimalMax(value = "999.99", message = "Cost cannot exceed 999.99")
  private Double cost;

  @NotEmpty(message = "Photo cannot be empty")
  private Byte[] photo;
}