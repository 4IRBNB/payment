package com.fourirbnb.payment.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fourirbnb.common.exception.ResourceNotFoundException;
import com.fourirbnb.payment.PaymentApplication;
import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.dto.UpdatePaymentRequestInternalDto;
import com.fourirbnb.payment.config.FeignConfig;
import com.fourirbnb.payment.domain.model.PaymentStatus;
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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = PaymentApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Slf4j
@Transactional
@Import(FeignConfig.class)
class PaymentServiceTest {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private PaymentRepository paymentRepository;

  private final UUID reservationId = UUID.randomUUID();

  private UUID paymentId;

  private PaymentStatus paymentStatus;

  @BeforeEach
  void setUp() {
    CreatePaymentRequestInternalDto internalDto = new CreatePaymentRequestInternalDto(
        reservationId, 400_000L, false
    );

    PaymentResponseInternalDto payment = paymentService.createPayment(internalDto);

    paymentId = payment.id();
    paymentStatus = PaymentStatus.valueOf(payment.paymentStatus());
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

  @Test
  @DisplayName("결제 단건 조회 테스트")
  @Order(2)
  void getPaymentById() {

    PaymentResponseInternalDto findPayment = paymentService.getPaymentById(paymentId);

    assertNotNull(findPayment);

    log.info("Payment Id : {}", findPayment.id());
    assertEquals(paymentId, findPayment.id());
  }

  @Test
  @DisplayName("결제 전체목록 조회 테스트")
  @Order(3)
  void getPayments() {

    Pageable pageable = PageRequest.of(0, 10);

    Page<PaymentResponseInternalDto> findPayments = paymentService.getPayments(pageable);

    log.info("Find Payments Size : {}", findPayments.getContent().size());

    assertNotNull(findPayments);
  }

  @Test
  @DisplayName("예약 정보로 결제 조회 테스트")
  @Order(4)
  void getPaymentsByReservationId() {

    PaymentResponseInternalDto findPayment = paymentService
        .getPaymentByReservationId(reservationId);

    log.info("Reservation Id : {}", reservationId);

    assertNotNull(findPayment);
    assertEquals(reservationId, findPayment.reservationId());
  }

  @Test
  @DisplayName("결제 상태 변경 테스트")
  @Order(5)
  void updatePaymentStatusById() {

    UpdatePaymentRequestInternalDto request = new UpdatePaymentRequestInternalDto("CANCELLED");

    PaymentResponseInternalDto updatePayment = paymentService
        .updatePaymentStatusById(paymentId, request);

    log.info("Payment Id : {}", paymentId);

    assertNotNull(updatePayment);
    assertNotEquals(paymentStatus.getStatus(), updatePayment.paymentStatus());
  }

  @Test
  @DisplayName("예약 정보로 결제 상태 변경 테스트")
  @Order(6)
  void updatePaymentStatusByReservationId() {

    UpdatePaymentRequestInternalDto request = new UpdatePaymentRequestInternalDto("CANCELLED");

    PaymentResponseInternalDto updatePayment = paymentService
        .updatePaymentStatusByReservationId(reservationId, request);

    log.info("Reservation Id : {}", reservationId);

    assertNotNull(updatePayment);
    assertNotEquals(paymentStatus.getStatus(), updatePayment.paymentStatus());
  }

  @Test
  @DisplayName("결제 삭제 테스트")
  @Order(7)
  void deletePayment() {

    PaymentResponseInternalDto deletePayment = paymentService.deletePaymentById(paymentId);

    log.info("Delete Payment Id : {}", deletePayment.id());

    entityManager.clear();

    assertThrows(ResourceNotFoundException.class, () -> {
      paymentService.getPaymentById(paymentId);
    });
  }
}