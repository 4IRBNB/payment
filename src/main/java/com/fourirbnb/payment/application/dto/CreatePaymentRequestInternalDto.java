package com.fourirbnb.payment.application.dto;

import java.util.UUID;

public record CreatePaymentRequestInternalDto(UUID reservationId, Long amount,
                                              Boolean couponUsage) {

}
