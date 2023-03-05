package com.julian.gymapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  private Long membershipSubscriptionId;
  @Column(name="start_date")
  private Date subscriptionStart;
  @Column(name="end_date")
  private Date subscriptionExpires;
  @Column(name="expired", columnDefinition = "boolean default false")
  private boolean expired;
  @Column(name="amount")
  private Short amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "membership_id", nullable = false)
  private Membership membership;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id")
  private Discount discount;
}
