package com.julian.gymapp.dto;

import com.julian.gymapp.security.SpringCrypto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
  private String currentPassword;
  private String newPassword;
  private Long userId;
  public String getCurrentPassword() {
    return SpringCrypto.decrypt(currentPassword);
  }
  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = SpringCrypto.encrypt(currentPassword);
  }

  public String getNewPassword() {
    return SpringCrypto.decrypt(newPassword);
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = SpringCrypto.encrypt(newPassword);
  }
}
