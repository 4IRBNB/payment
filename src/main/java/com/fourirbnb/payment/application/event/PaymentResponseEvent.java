package com.fourirbnb.payment.application.event;

import java.util.UUID;

public record PaymentResponseEvent(UUID paymentId, UUID reservationId, Boolean success) {

}
