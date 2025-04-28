package com.fourirbnb.payment.application.event;

import java.util.UUID;

public record PaymentCancelResponseEvent(UUID reservationId, Boolean cancelled) {

}
