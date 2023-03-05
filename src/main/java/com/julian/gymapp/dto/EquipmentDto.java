package com.julian.gymapp.dto;

import com.julian.gymapp.domain.enums.EquipmentType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
  @NotNull
  @NotBlank
  @Size(max = 80)
  private String name;

  @NotNull
  private EquipmentType type;

  @Min(value = 0, message = "Quantity cannot be negative")
  @Max(value = 99999, message = "Quantity cannot be greater than 99999")
  private Short quantity;

  @NotNull
  private Long exerciseId;
}
