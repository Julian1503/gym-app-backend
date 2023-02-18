package com.julian.gymapp.controller;

import com.julian.gymapp.model.LoginRequest;
import com.julian.gymapp.service.ISecurityService;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

  private final ISecurityService securityService;
  private final AuthenticationManager authorizationManager;
  @PostMapping("/login")
  public String token(@RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authorizationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
      Consumer<Map<String, Object>> claims = securityService.getClaims();
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return securityService.generateToken(authentication, claims);
    } catch(Exception e) {
      return e.getMessage();
    }
  }
}
