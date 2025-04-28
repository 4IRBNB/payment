package com.fourirbnb.payment.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourirbnb.payment.application.event.PaymentCancelResponseEvent;
import com.fourirbnb.payment.domain.port.PaymentCancelResponsePublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCancelHandler {

  private final PaymentProcessor processor;
  private final PaymentCancelResponsePublisher publisher;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "payment-cancel-request-topic", groupId = "payment-group")
  public void listen(String message) {
    try {
      UUID reservationId = UUID.fromString(message);
      PaymentCancelResponseEvent response = processor.cancel(reservationId);
      publisher.publish(response);
    } catch (Exception e) {
      // log
    }
  }
}
