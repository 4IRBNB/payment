package com.fourirbnb.payment.presentation.controller;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.service.PaymentService;
import com.fourirbnb.payment.presentation.dto.CreatePaymentRequestDto;
import com.fourirbnb.payment.presentation.dto.PaymentResponseDto;
import com.fourirbnb.payment.presentation.mapper.PaymentDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
  private final PaymentService paymentService;

  @PostMapping("")
  public BaseResponse<PaymentResponseDto> createPayment(
      @RequestBody CreatePaymentRequestDto request) {

    CreatePaymentRequestInternalDto internalDto = PaymentDtoMapper.toCreateInternalDto(request);

    PaymentResponseInternalDto response = paymentService.createPayment(internalDto);

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponse(response),
        "결제 성공", HttpStatus.CREATED.value()
    );
  }
}
