package com.julian.gymapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto extends PersonDto {

  private String memberNumber;

  @NotNull(message = "Join date is required")
  @PastOrPresent(message = "Join date cannot be in the future")
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate joinDate;

  @Size(max = 70, message = "Emergency contact name must be at most 70 characters")
  private String emergencyContactName;

  @Pattern(regexp = "^$|^\\+?\\d{1,3}\\-?\\d{7,15}$", message = "Invalid emergency contact phone format")
  private String emergencyContactPhone;
}