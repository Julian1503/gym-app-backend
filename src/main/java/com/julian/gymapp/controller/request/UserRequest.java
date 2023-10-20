package com.julian.gymapp.controller.request;

import java.util.List;
import lombok.Data;

@Data
public class UserRequest {
  private String email;
  private String username;
  private String password;
  private Long memberId;
  private List<String> roles;
}
