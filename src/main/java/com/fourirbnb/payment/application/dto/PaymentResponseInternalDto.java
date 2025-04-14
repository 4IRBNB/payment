package com.fourirbnb.payment.application.dto;

import java.util.UUID;

public record PaymentResponseInternalDto(UUID id, UUID reservationId, Long amount,
                                         Boolean couponUsage, String paymentStatus) {

}
