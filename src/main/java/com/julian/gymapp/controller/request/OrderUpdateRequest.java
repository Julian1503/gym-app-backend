package com.julian.gymapp.controller.request;

import lombok.Data;

@Data
public class OrderUpdateRequest {
  private long eventToMove;
  private long eventToSwap;
}