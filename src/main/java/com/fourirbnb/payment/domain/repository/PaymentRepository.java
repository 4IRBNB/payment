package com.fourirbnb.payment.domain.repository;

import com.fourirbnb.payment.domain.model.Payment;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {

  void save(Payment payment);

  boolean existsByReservationId(UUID reservationId);
}
