package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="membership_subscription")
@NoArgsConstructor
@AllArgsConstructor
public class MembershipSubscription {
  @Id
  @Column(name="membership_subscription_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_subscription_id_seq")
  @SequenceGenerator(name = "membership_subscription_id_seq", sequenceName = "membership_subscription_id_seq", allocationSize = 1)
  private Long membershipSubscriptionId;
  @Column(name="start_date")
  private Date subscriptionStart;
  @Column(name="end_date")
  private Date subscriptionExpires;
  @Column(name="expired", columnDefinition = "boolean default false")
  private boolean expired;
  @Column(name="amount")
  private Short amount;
  @Column(name="membership_id")
  private Long membershipId;
  @Column(name = "member_id")
  private Long memberId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id")
  private Discount discount;
  @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean isDeleted;
}
