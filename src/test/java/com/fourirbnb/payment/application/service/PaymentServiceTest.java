package com.fourirbnb.payment.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fourirbnb.payment.PaymentApplication;
import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.domain.repository.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = PaymentApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Slf4j
@Transactional
class PaymentServiceTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private PaymentRepository paymentRepository;

  private final UUID reservationId = UUID.randomUUID();

  private UUID paymentId;

  @BeforeEach
  void setUp() {
    CreatePaymentRequestInternalDto internalDto = new CreatePaymentRequestInternalDto(
        reservationId, 400_000L, false
    );

    PaymentResponseInternalDto payment = paymentService.createPayment(internalDto);

    paymentId = payment.id();
  }

  @Test
  @DisplayName("결제 생성 테스트")
  @Order(1)
  void createPayment() {

    CreatePaymentRequestInternalDto internalDto = new CreatePaymentRequestInternalDto(
        UUID.randomUUID(), 400_000L, false
    );

    PaymentResponseInternalDto payment = paymentService.createPayment(internalDto);

    log.info("Created Payment : {}", payment.toString());

    assertNotNull(payment);
  }
}