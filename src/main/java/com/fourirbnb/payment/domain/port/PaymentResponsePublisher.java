package com.fourirbnb.payment.domain.port;

import com.fourirbnb.payment.application.event.PaymentResponseEvent;

public interface PaymentResponsePublisher {

  void publish(PaymentResponseEvent event);
}