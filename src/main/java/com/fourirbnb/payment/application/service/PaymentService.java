package com.fourirbnb.payment.application.service;

import com.fourirbnb.common.exception.ResourceNotFoundException;
import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.mapper.PaymentMapper;
import com.fourirbnb.payment.domain.model.Payment;
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

  @Transactional
  public PaymentResponseInternalDto createPayment(CreatePaymentRequestInternalDto internalDto) {

    Payment payment = PaymentMapper.toEntity(internalDto);

    paymentDomainService.validatePaymentAvailable(payment);

    paymentRepository.save(payment);

    return PaymentMapper.toResponse(payment);
  }

  @Transactional(readOnly = true)
  public PaymentResponseInternalDto getPaymentById(UUID paymentId) {

    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new ResourceNotFoundException("결제 조회 실패 : 존재하지 않는 결제 정보"));

    return PaymentMapper.toResponse(payment);
  }

  public Page<PaymentResponseInternalDto> getPayments(Pageable pageable) {

    Page<Payment> paymentPage = paymentRepository.findAll(pageable);

    if(!paymentPage.hasContent()) {
      throw new ResourceNotFoundException("결제 조회 실패 : 결제 목록 없음");
    }
    return PaymentMapper.toResponsePage(paymentPage);
  }

  public PaymentResponseInternalDto getPaymentByReservationId(UUID reservationId) {

    Payment payment = paymentRepository.findByReservationId(reservationId)
        .orElseThrow(() -> new ResourceNotFoundException("결제 조회 실패 : 예약 정보 없음"));

    return PaymentMapper.toResponse(payment);
  }
}
