package com.fourirbnb.payment.presentation.mapper;

import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.dto.UpdatePaymentRequestInternalDto;
import com.fourirbnb.payment.presentation.dto.CreatePaymentRequestDto;
import com.fourirbnb.payment.presentation.dto.PaymentResponseDto;
import com.fourirbnb.payment.presentation.dto.UpdatePaymentRequestDto;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentDtoMapper {

  public static CreatePaymentRequestInternalDto toCreateInternalDto(
      CreatePaymentRequestDto request) {

    return new CreatePaymentRequestInternalDto(
        request.reservationId(),
        request.amount(),
        request.couponUsage()
    );
  }

  public static UpdatePaymentRequestInternalDto toUpdateInternalDto(
      UpdatePaymentRequestDto request
  ) {
    return new UpdatePaymentRequestInternalDto(
        request.paymentStatus()
    );
  }

  public static PaymentResponseDto toResponse(PaymentResponseInternalDto response) {

    return new PaymentResponseDto(
        response.id(), response.reservationId(), response.amount(),
        response.couponUsage(), response.paymentStatus()
    );
  }

  public static List<PaymentResponseDto> toResponseList(
      List<PaymentResponseInternalDto> responses) {

    return responses.stream()
        .map(PaymentDtoMapper::toResponse)
        .collect(Collectors.toList());
  }
}
