package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Person {
  @Id
  @Column(name="person_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long personId;
  @Column(name="member_number", nullable = false)
  private String memberNumber;
  @Column(name="join_date", nullable = false)
  private Date joinDate;
  @Column(name="emergency_contact_name", length = 70)
  private String emergencyContactName;
  @Column(name="emergency_contact_phone", length = 15)
  private String emergencyContactPhone;

  @OneToMany(fetch = FetchType.LAZY)
  private List<MembershipSubscription> membershipSubscriptions;
  @OneToMany(fetch = FetchType.LAZY)
  private List<Plan> plans;
}
