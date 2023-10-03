package com.julian.gymapp.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class MembershipSubscriptionDto {

  private Long membershipSubscriptionId;

  @NotNull(message = "Subscription start date is required")
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate subscriptionStart;

  @NotNull(message = "Subscription expiration date is required")
  @DateTimeFormat(iso = ISO.DATE, pattern = "yyyy-MM-dd")
  private LocalDate subscriptionExpires;

  private boolean expired;

  private Long memberId;

  private Long membershipId;

  private DiscountDto discount;
  private Short amount;
}