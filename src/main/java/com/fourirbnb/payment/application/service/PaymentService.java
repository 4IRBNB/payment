package com.fourirbnb.payment.application.service;

import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.mapper.PaymentMapper;
import com.fourirbnb.payment.domain.model.Payment;
import com.fourirbnb.payment.domain.repository.PaymentRepository;
import com.fourirbnb.payment.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
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
}
