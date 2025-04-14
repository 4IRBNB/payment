package com.fourirbnb.payment.application.mapper;

import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.domain.model.Payment;
import com.fourirbnb.payment.domain.model.PaymentStatus;
import com.fourirbnb.payment.presentation.dto.CreatePaymentRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PaymentMapper {

  public static Payment toEntity(CreatePaymentRequestInternalDto request) {

    return new Payment(request.reservationId(), request.amount(),
        request.couponUsage(), PaymentStatus.COMPLETED);
  }

  public static PaymentResponseInternalDto toResponse(Payment payment) {

    return new PaymentResponseInternalDto(
        payment.getId(), payment.getReservationId(), payment.getAmount(),
        payment.getCouponUsage(), payment.getPaymentStatus().getStatus()
    );
  }

  public static List<PaymentResponseInternalDto> toResponseList(List<Payment> paymentList) {

    return paymentList.stream()
        .map(PaymentMapper::toResponse)
        .collect(Collectors.toList());
  }

  public static Page<PaymentResponseInternalDto> toResponsePage(Page<Payment> paymentPage) {

    List<PaymentResponseInternalDto> paymentList = paymentPage.getContent().stream()
        .map(PaymentMapper::toResponse)
        .collect(Collectors.toList());

    return new PageImpl<>(paymentList, paymentPage.getPageable(), paymentPage.getTotalElements());
  }
}
