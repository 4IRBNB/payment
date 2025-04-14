package com.fourirbnb.payment.presentation.dto;

import java.util.UUID;

public record CreatePaymentRequestDto(UUID reservationId, Long amount, Boolean couponUsage) {

}
