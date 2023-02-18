package com.julian.gymapp.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@Data @AllArgsConstructor @NoArgsConstructor
public class ErrorBase{
  private String error;
  private String code;
  private String cause;
}
