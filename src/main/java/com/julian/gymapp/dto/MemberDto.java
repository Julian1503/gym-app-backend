package com.julian.gymapp.dto;

import com.julian.gymapp.domain.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto extends PersonDto {

  @NotNull(message = "Member number is required")
  @NotBlank(message = "Member number cannot be blank")
  @Size(max = 50, message = "Member number must be at most 50 characters")
  private String memberNumber;

  @NotNull(message = "Join date is required")
  @PastOrPresent(message = "Join date cannot be in the future")
  private Date joinDate;

  @Size(max = 70, message = "Emergency contact name must be at most 70 characters")
  private String emergencyContactName;

  @Pattern(regexp = "^\\+?[0-9]{1,3}\\-?[0-9]{7,15}$", message = "Invalid emergency contact phone format")
  private String emergencyContactPhone;
}