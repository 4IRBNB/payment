package com.fourirbnb.payment.application.service;

import com.fourirbnb.common.exception.InternalServerException;
import com.fourirbnb.common.exception.ResourceNotFoundException;
import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.dto.UpdatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.mapper.PaymentMapper;
import com.fourirbnb.payment.domain.model.Payment;
import com.fourirbnb.payment.domain.model.PaymentStatus;
import com.fourirbnb.payment.domain.model.ReservationData;
import com.fourirbnb.payment.domain.port.ReservationPort;
import com.fourirbnb.payment.domain.repository.PaymentRepository;
import com.fourirbnb.payment.domain.service.PaymentDomainService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final PaymentDomainService paymentDomainService;
  private final ReservationPort reservationPort;

  @Transactional
  public PaymentResponseInternalDto createPayment(CreatePaymentRequestInternalDto internalDto) {

    Payment payment = PaymentMapper.toEntity(internalDto);

    paymentDomainService.validatePaymentAvailable(payment);

    paymentRepository.save(payment);

    return PaymentMapper.toResponse(payment);
  }

  @Transactional(readOnly = true)
  public PaymentResponseInternalDto getPaymentById(UUID paymentId) {

    Payment payment = findPaymentById(paymentId);

    return PaymentMapper.toResponse(payment);
  }

  @Transactional(readOnly = true)
  public Page<PaymentResponseInternalDto> getPayments(Pageable pageable) {

    Page<Payment> paymentPage = paymentRepository.findAll(pageable);

    isPageHasContent(paymentPage);

    return PaymentMapper.toResponsePage(paymentPage);
  }

  @Transactional(readOnly = true)
  public PaymentResponseInternalDto getPaymentByReservationId(UUID reservationId) {

    Payment payment = findPaymentByReservationId(reservationId);

    return PaymentMapper.toResponse(payment);
  }

  @Transactional
  public PaymentResponseInternalDto updatePaymentStatusById(
      UUID paymentId, UpdatePaymentRequestInternalDto request) {

    Payment payment = findPaymentById(paymentId);

    payment.update(PaymentStatus.valueOf(request.paymentStatus()));

    paymentRepository.save(payment);

    try {

      ReservationData data = reservationPort.toDomainModel(
          reservationPort.updateReservationStatus(payment.getReservationId(), "CANCELLED")
      );

    } catch (Exception e) {

      payment.update(PaymentStatus.COMPLETED);

      paymentRepository.save(payment);

      throw new InternalServerException(e.getMessage());
    }

    return PaymentMapper.toResponse(payment);
  }

  @Transactional
  public PaymentResponseInternalDto updatePaymentStatusByReservationId(
      UUID reservationId, UpdatePaymentRequestInternalDto internalDto) {

    Payment payment = findPaymentByReservationId(reservationId);

    payment.update(PaymentStatus.valueOf(internalDto.paymentStatus()));

    paymentRepository.save(payment);

    try {

      ReservationData data = reservationPort.toDomainModel(
          reservationPort.updateReservationStatus(reservationId, "CANCELLED")
      );

    } catch (Exception e) {

      payment.update(PaymentStatus.COMPLETED);

      paymentRepository.save(payment);

      throw new InternalServerException(e.getMessage());
    }

    return PaymentMapper.toResponse(payment);
  }

  @Transactional
  public PaymentResponseInternalDto deletePaymentById(UUID paymentId) {

    Payment payment = findPaymentById(paymentId);

    payment.delete(1L);

    paymentRepository.save(payment);

    return PaymentMapper.toResponse(payment);
  }

  private Payment findPaymentById(UUID paymentId) {

    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new ResourceNotFoundException("Payment Not Found : " + paymentId));
  }

  private Payment findPaymentByReservationId(UUID reservationId) {

    return paymentRepository.findByReservationId(reservationId)
        .orElseThrow(() -> new ResourceNotFoundException("Payment Not Found : " + reservationId));
  }

  private void isPageHasContent(Page<Payment> paymentPage) {

    if (!paymentPage.hasContent()) {
      throw new ResourceNotFoundException("Not Found Payment Page");
    }
  }
}
