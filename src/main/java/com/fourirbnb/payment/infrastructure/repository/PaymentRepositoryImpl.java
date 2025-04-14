package com.fourirbnb.payment.infrastructure.repository;

import com.fourirbnb.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

  private final PaymentJpaRepository jpaRepository;
}
