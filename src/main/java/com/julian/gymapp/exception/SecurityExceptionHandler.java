package com.julian.gymapp.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.julian.gymapp.controller.response.BaseResponse;
import com.julian.gymapp.exception.error.ErrorBase;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorBase> handleBadCredentialsException(BadCredentialsException ex) {
    ErrorBase errorResponse = new ErrorBase("Bad Credentials", HttpStatus.UNAUTHORIZED.toString(), "Invalid username or password");
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorBase> handleAccessDeniedException(AccessDeniedException ex) {
    ErrorBase errorResponse = new ErrorBase("No permissions", HttpStatus.FORBIDDEN.toString(),"Access denied");
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    List<String> errorMessages = bindingResult.getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());
    BaseResponse errorResponse = new BaseResponse();
    errorResponse.setErrorResponse(errorMessages);
    errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    errorResponse.setMessage("Validation Error");
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    Throwable mostSpecificCause = ex.getMostSpecificCause();
    if (mostSpecificCause instanceof InvalidFormatException) {
      InvalidFormatException exception = (InvalidFormatException) mostSpecificCause;
      String fieldName = exception.getPath().get(0).getFieldName();
      Object value = exception.getValue();
      BaseResponse errorResponse = new BaseResponse();
      errorResponse.setErrorResponse(List.of("Invalid value '" + value + "' for field '" + fieldName + "'."));
      errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
      errorResponse.setMessage("Validation Error");
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.badRequest().build();
  }
}
