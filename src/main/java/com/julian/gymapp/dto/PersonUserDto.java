package com.julian.gymapp.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonUserDto {
  private String email;
  private String username;
  private String password;
  private String identity;
  private String name;
  private String lastname;
  private LocalDate birthDate;
}
