package com.fourirbnb.payment.domain.port;

import com.fourirbnb.payment.application.event.PaymentCancelResponseEvent;
import com.fourirbnb.payment.application.event.PaymentResponseEvent;

public interface PaymentCancelResponsePublisher {

  void publish(PaymentCancelResponseEvent event);
}
