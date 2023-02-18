package com.julian.gymapp.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.gymapp.error.ErrorBase;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(jakarta.servlet.http.HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response,
      org.springframework.security.core.AuthenticationException authException)
      throws IOException, jakarta.servlet.ServletException {

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    ErrorBase errorResponse = new ErrorBase("Unauthorized","401", "Missing token");

    ObjectMapper mapper = new ObjectMapper();
    String responseJson = mapper.writeValueAsString(errorResponse);

    response.getWriter().write(responseJson);
  }
}