package com.fourirbnb.payment.application.service;

import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.event.PaymentCancelResponseEvent;
import com.fourirbnb.payment.application.event.PaymentRequestEvent;
import com.fourirbnb.payment.application.event.PaymentResponseEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProcessor {

  private final PaymentService paymentService;

  public PaymentResponseEvent process(PaymentRequestEvent event) {
    Boolean success = true;

    PaymentResponseInternalDto response = paymentService.createPaymentFromEvent(event);

    return new PaymentResponseEvent(event.reservationId(), response.id(), success);
  }

  public PaymentCancelResponseEvent cancel(UUID reservationId) {
    Boolean cancelled = true;
    return new PaymentCancelResponseEvent(reservationId, cancelled);
  }
}
