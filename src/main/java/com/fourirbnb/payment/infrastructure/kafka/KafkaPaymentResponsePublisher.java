package com.fourirbnb.payment.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourirbnb.common.exception.InternalServerException;
import com.fourirbnb.payment.application.event.PaymentResponseEvent;
import com.fourirbnb.payment.domain.port.PaymentResponsePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPaymentResponsePublisher implements PaymentResponsePublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;
  ;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(PaymentResponseEvent event) {
    try {
      String json = objectMapper.writeValueAsString(event);
      kafkaTemplate.send("payment-response-topic", json);
    } catch (Exception e) {
      log.info("결제 실패 : {}", e.getMessage());
      throw new InternalServerException(e.getMessage());
    }
  }
}
