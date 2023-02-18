package com.julian.gymapp.model;

import com.julian.gymapp.domain.User;
import com.julian.gymapp.security.SpringCrypto;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUser implements UserDetails {
  private static final long serialVersionUID = 1L;
  private User user;
  public JwtUser(User user) {
    this.user = user;
  }

  public String getUsername() {
    return user.getUsername();
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRolNames().stream().map(SimpleGrantedAuthority::new).toList();
  }

  public String getPassword() {
    return user.getPassword();
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }
}
