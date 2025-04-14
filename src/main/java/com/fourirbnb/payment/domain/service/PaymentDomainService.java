package com.fourirbnb.payment.domain.service;

import com.fourirbnb.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDomainService {

  private final PaymentRepository paymentRepository;
}
