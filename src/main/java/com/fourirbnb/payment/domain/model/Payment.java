package com.fourirbnb.payment.domain.model;

import com.fourirbnb.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@Getter
@NoArgsConstructor
@Entity
@FilterDef(name = "deletedFilter")
@Filter(name = "deletedFilter", condition = "deleted_at IS NULL")
@Table(name = "p_payment")
public class Payment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private UUID reservationId;

  @Column(nullable = false)
  private Long amount;

  @Column(nullable = false)
  private Boolean couponUsage;

  @Column(nullable = false)
  private PaymentStatus paymentStatus;

  public Payment(UUID reservationId, Long amount,
      Boolean couponUsage, PaymentStatus paymentStatus) {

    this.reservationId = reservationId;
    this.amount = amount;
    this.couponUsage = couponUsage;
    this.paymentStatus = paymentStatus;
  }

  public void update(PaymentStatus paymentStatus) {

    this.paymentStatus = paymentStatus;
  }
}
