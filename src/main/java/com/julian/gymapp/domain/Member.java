package com.julian.gymapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "person_id")
public class Member extends Person {
  @Column(name="member_number", nullable = false, length = 50)
  private String memberNumber;
  @Column(name="join_date", nullable = false)
  private Date joinDate;
  @Column(name="emergency_contact_name", length = 70)
  private String emergencyContactName;
  @Column(name="emergency_contact_phone", length = 15)
  private String emergencyContactPhone;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "membership_subscription",
      joinColumns = @JoinColumn(name = "member_id"),
      inverseJoinColumns = @JoinColumn(name = "membership_subscription_id"))
  @JsonIgnore
  private List<MembershipSubscription> membershipSubscriptions;
}
