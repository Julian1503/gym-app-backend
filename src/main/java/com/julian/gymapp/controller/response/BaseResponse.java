package com.julian.gymapp.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class BaseResponse {
  private Object response;
  private HttpStatus status = HttpStatus.OK;
  private String message;
  private boolean success = true;
  private List<String> errorResponse;
}
