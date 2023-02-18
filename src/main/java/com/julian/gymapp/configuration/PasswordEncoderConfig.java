package com.julian.gymapp.configuration;

import com.julian.gymapp.security.SpringCrypto;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderConfig implements PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    return SpringCrypto.encrypt(rawPassword.toString());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return rawPassword.equals(SpringCrypto.decrypt(encodedPassword));
  }

  @Override
  public boolean upgradeEncoding(String encodedPassword) {
    return PasswordEncoder.super.upgradeEncoding(encodedPassword);
  }
}
