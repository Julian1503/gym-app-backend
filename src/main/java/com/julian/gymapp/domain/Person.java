package com.julian.gymapp.domain;

import com.julian.gymapp.domain.converter.GenderConverter;
import com.julian.gymapp.domain.converter.IdentifierTypeConverter;
import com.julian.gymapp.domain.enums.Gender;
import com.julian.gymapp.domain.enums.IdentifierType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table (name = "person")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_person_id_seq")
  @SequenceGenerator(name = "person_person_id_seq", sequenceName = "person_person_id_seq", allocationSize = 1)
  @Column(name="person_id", nullable = false, unique = true)
  private Long personId;
  @Column(name="name", nullable = false, length = 50)
  private String name;
  @Column(name="last_name", nullable = false, length = 50)
  private String lastName;
  @Column(name="identifier", length = 9, unique = true)
  private String identifier;
  @Column(name="identifier_type", nullable = false, columnDefinition = "identifiertype")
  @Convert(converter = IdentifierTypeConverter.class)
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
  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Column(name="birth_date", nullable = false)
  private Date birthDate;
}