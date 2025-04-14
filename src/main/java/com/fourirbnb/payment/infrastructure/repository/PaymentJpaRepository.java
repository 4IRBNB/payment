package com.fourirbnb.payment.infrastructure.repository;

import com.fourirbnb.payment.domain.model.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {

}
