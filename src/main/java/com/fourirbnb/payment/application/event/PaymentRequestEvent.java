package com.fourirbnb.payment.application.event;

import java.util.UUID;

public record PaymentRequestEvent(UUID reservationId, Long amount, Boolean couponUsage) {

}