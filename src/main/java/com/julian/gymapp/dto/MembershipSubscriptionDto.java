package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;

@Data
public class MembershipSubscriptionDto {

  private Long membershipSubscriptionId;

  @NotNull(message = "Subscription start date is required")
  private Date subscriptionStart;

  @NotNull(message = "Subscription expiration date is required")
  private Date subscriptionExpires;

  private boolean expired;

  @NotNull(message = "Member is required")
  private MemberDto member;

  @NotNull(message = "Membership is required")
  private MembershipDto membership;

  private DiscountDto discount;
}