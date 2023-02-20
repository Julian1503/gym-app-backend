package com.julian.gymapp.exception;

import com.julian.gymapp.exception.error.ErrorBase;
import java.nio.file.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
}
