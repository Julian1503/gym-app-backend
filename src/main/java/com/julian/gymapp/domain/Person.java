package com.julian.gymapp.domain;

import com.julian.gymapp.domain.enums.Gender;
import com.julian.gymapp.domain.enums.IdentifierType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table (name = "person")
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="person_id", nullable = false)
  private Long personId;
  @Column(name="name", nullable = false, length = 50)
  private String name;
  @Column(name="last_name", nullable = false, length = 50)
  private String lastName;
  @Column(name="identifier", length = 9)
  private String identifier;
  @Column(name="identifier_type", nullable = false)
  private IdentifierType identifierType;
  @Column(name="phone_number", length = 15)
  private String phoneNumber;
  @Column(name="finger_print_data")
  private Byte[] fingerPrintData;
  @Column(name="photo")
  private Byte[] photo;
  @Column(name="street", length = 50)
  private String street;
  @Column(name="house_number", length = 10)
  private String houseNumber;
  @Column(name="floor", length = 4)
  private String floor;
  @Column(name="door", length = 2)
  private String door;
  @Column(name="gender", nullable = false)
  private Gender gender;
  @Column(name="birth_date", nullable = false)
  private Date birthDate;
}
