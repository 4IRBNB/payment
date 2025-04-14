package com.fourirbnb.payment.infrastructure.repository;

import com.fourirbnb.payment.domain.model.Payment;
import com.fourirbnb.payment.domain.repository.PaymentRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Optional<Payment> findById(UUID paymentId) {

    return jpaRepository.findById(paymentId);
  }

  @Override
  public Optional<Payment> findByReservationId(UUID reservationId) {

    return jpaRepository.findByReservationId(reservationId);
  }

  @Override
  public Page<Payment> findAll(Pageable pageable) {

    return jpaRepository.findAll(pageable);
  }

  @Override
  public void deleteById(UUID paymentId) {

    jpaRepository.deleteById(paymentId);
  }

  @Override
  public boolean existsByReservationId(UUID reservationId) {

    return jpaRepository.existsByReservationId(reservationId);
  }
}
