package com.fourirbnb.payment.infrastructure.repository;

import com.fourirbnb.payment.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {

  boolean existsByReservationId(UUID reservationId);

  Optional<Payment> findByReservationId(UUID reservationId);
}
