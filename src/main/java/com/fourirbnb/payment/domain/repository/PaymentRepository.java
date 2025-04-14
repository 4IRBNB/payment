package com.fourirbnb.payment.domain.repository;

import com.fourirbnb.payment.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {

  void save(Payment payment);

  boolean existsByReservationId(UUID reservationId);

  Optional<Payment> findById(UUID paymentId);

  Page<Payment> findAll(Pageable pageable);

  Optional<Payment> findByReservationId(UUID reservationId);
}
