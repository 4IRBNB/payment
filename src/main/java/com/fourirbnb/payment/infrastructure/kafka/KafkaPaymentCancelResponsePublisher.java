package com.fourirbnb.payment.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourirbnb.payment.application.event.PaymentCancelResponseEvent;
import com.fourirbnb.payment.domain.port.PaymentCancelResponsePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPaymentCancelResponsePublisher implements PaymentCancelResponsePublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(PaymentCancelResponseEvent event) {
    try {
      String json = objectMapper.writeValueAsString(event);
      kafkaTemplate.send("payment-cancel-response", json);
    } catch (Exception e) {
      log.info("결제 취소 실패 : {}", e.getMessage());
    }
  }
}
