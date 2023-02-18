package com.julian.gymapp.configuration;

import com.julian.gymapp.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext {
  private final Logger LOGGER = LoggerFactory.getLogger(SecurityContext.class);
  @Autowired
  SecurityService jwtTokenUtil;

  public SecurityContext() {
  }

  public void addTokenToSecurityContext(final String token) {
    if (this.jwtTokenUtil.validateToken(token)) {
      this.setSecurityContext(token);
    } else {
      this.LOGGER.debug("Cannot set SecurityContext - the token supplied is invalid.");
    }
  }

  private void setSecurityContext(final String token) {
    UserDetails userDetails = this.jwtTokenUtil.getUserDetails(token);
    if (userDetails != null) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
      authentication.setDetails(userDetails);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      this.LOGGER.debug("Cannot set SecurityContext - cannot extract user details from the token supplied.");
    }

  }
}
