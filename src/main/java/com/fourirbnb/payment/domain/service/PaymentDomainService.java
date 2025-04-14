package com.fourirbnb.payment.domain.service;

import com.fourirbnb.common.exception.DuplicateResourceException;
import com.fourirbnb.payment.domain.model.Payment;
import com.fourirbnb.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDomainService {

  private final PaymentRepository paymentRepository;

  public void validatePaymentAvailable(Payment payment) {

    if(paymentRepository.existsByReservationId(payment.getReservationId())) {
      throw new DuplicateResourceException("결제를 생성할 수 없습니다 : 이미 처리된 결제");
    }
  }
}
