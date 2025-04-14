package com.fourirbnb.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
  COMPLETED("COMPLETED"),
  CANCELLED("CANCELLED");

  private final String status;
}
