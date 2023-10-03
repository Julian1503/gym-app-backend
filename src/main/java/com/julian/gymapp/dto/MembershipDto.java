package com.julian.gymapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class MembershipDto {
  private Long membershipId;

  @NotBlank(message = "Name cannot be blank")
  @Size(max = 50, message = "Name cannot exceed 50 characters")
  private String name;

  @Size(max = 200, message = "Description cannot exceed 200 characters")
  private String description;

  @Min(value = 1, message = "Days must be at least 1")
  @Max(value = 9999, message = "Days cannot exceed 9999")
  private Short days;
  private Double price;
}