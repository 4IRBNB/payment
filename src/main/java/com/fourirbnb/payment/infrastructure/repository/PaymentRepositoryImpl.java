package com.fourirbnb.payment.infrastructure.repository;

import com.fourirbnb.payment.domain.model.Payment;
import com.fourirbnb.payment.domain.repository.PaymentRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

  private final PaymentJpaRepository jpaRepository;

  @Override
  public void save(Payment payment) {

    jpaRepository.save(payment);
  }

  @Override
  public boolean existsByReservationId(UUID reservationId) {

    return jpaRepository.existsByReservationId(reservationId);
  }
}
