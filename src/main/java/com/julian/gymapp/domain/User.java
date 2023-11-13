package com.julian.gymapp.domain;

import com.julian.gymapp.security.SpringCrypto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(name = "avatar")
  private byte[] avatar;
  @Column(name = "username", nullable = false)
  private String username;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
  public void setPassword(String password) {
    this.password = SpringCrypto.encrypt(password);
  }
  public List<String> getRolNames() {
    if(!roles.isEmpty()) {
      return roles.stream().map(Role::getName).toList();
    }
    return new ArrayList<>();
  }
  @ManyToMany
  @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
  private List<Role> roles;
  @OneToOne
  @JoinColumn(name = "person_id", referencedColumnName = "person_id")
  private Person user;

  public User(String username, List<Role> roles) {
    this.username = username;
    this.roles = roles;
  }
}