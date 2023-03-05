package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
  private Long id;
  @NotBlank(message = "Role name cannot be blank")
  @Size(max = 100, message = "Role name cannot exceed 100 characters")
  private String name;

  public RoleDto(String name) { this.name = name; }
}