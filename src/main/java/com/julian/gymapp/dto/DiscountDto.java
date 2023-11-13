package com.julian.gymapp.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {
  private Long discountId;

  @NotNull
  @DecimalMin(value = "0.01", message = "Minimum percentage is 0.01")
  @DecimalMax(value = "100.00", message = "Maximum percentage is 100.00")
  private Double percentage;

  @NotBlank(message = "The code must not be empty")
  @NotNull(message = "The code must not be null")
  @Size(max = 100, message = "Code must be 100 characters")
  private String code;

  @NotNull
  private boolean expired;
}