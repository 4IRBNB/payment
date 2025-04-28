package com.fourirbnb.payment.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourirbnb.common.exception.InternalServerException;
import com.fourirbnb.payment.application.event.PaymentRequestEvent;
import com.fourirbnb.payment.application.event.PaymentResponseEvent;
import com.fourirbnb.payment.domain.port.PaymentResponsePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestListener {

  private final PaymentProcessor paymentProcessor;
  private final PaymentResponsePublisher paymentResponsePublisher;
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "payment-request-topic", groupId = "payment-group")
  public void listen(String message) {
    try {
      PaymentRequestEvent request = objectMapper.readValue(message, PaymentRequestEvent.class);
      PaymentResponseEvent response = paymentProcessor.process(request);
      paymentResponsePublisher.publish(response);
    } catch (Exception e) {
      log.info("결제 실패 : {}",e.getMessage());
      throw new InternalServerException(e.getMessage());
    }
  }
}
