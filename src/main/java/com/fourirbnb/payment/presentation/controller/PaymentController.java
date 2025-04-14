package com.fourirbnb.payment.presentation.controller;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.common.response.Pagination;
import com.fourirbnb.payment.application.dto.CreatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.dto.PaymentResponseInternalDto;
import com.fourirbnb.payment.application.dto.UpdatePaymentRequestInternalDto;
import com.fourirbnb.payment.application.service.PaymentService;
import com.fourirbnb.payment.presentation.dto.CreatePaymentRequestDto;
import com.fourirbnb.payment.presentation.dto.PaymentResponseDto;
import com.fourirbnb.payment.presentation.dto.UpdatePaymentRequestDto;
import com.fourirbnb.payment.presentation.mapper.PaymentDtoMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/{paymentId}")
  public BaseResponse<PaymentResponseDto> getPaymentById(@PathVariable UUID paymentId) {

    PaymentResponseInternalDto response = paymentService.getPaymentById(paymentId);

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponse(response)
        , "결제 단건 조회 성공 : " + paymentId, HttpStatus.OK.value()
    );
  }

  @GetMapping("")
  public BaseResponse<List<PaymentResponseDto>> getPayments(Pageable pageable) {

    Page<PaymentResponseInternalDto> responsePage = paymentService.getPayments(pageable);

    Pagination pagination = new Pagination(
        responsePage.getTotalPages(),
        responsePage.getTotalElements(),
        responsePage.getNumber(),
        responsePage.getNumberOfElements()
    );

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponseList(responsePage.getContent()),
        "결제 전체 목록 조회 완료", pagination
    );
  }

  @GetMapping("/reservation")
  public BaseResponse<PaymentResponseDto> getPaymentByReservationId(
      @RequestParam UUID reservationId) {

    PaymentResponseInternalDto response = paymentService.getPaymentByReservationId(reservationId);

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponse(response),
        "예약 정보로 결제 조회 성공", HttpStatus.OK.value()
    );
  }

  @PatchMapping("/{paymentId}")
  public BaseResponse<PaymentResponseDto> updatePaymentStatus(
      @PathVariable UUID paymentId, @RequestBody UpdatePaymentRequestDto request) {

    UpdatePaymentRequestInternalDto internalDto = PaymentDtoMapper.toUpdateInternalDto(request);

    PaymentResponseInternalDto response = paymentService
        .updatePaymentStatusById(paymentId, internalDto);

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponse(response),
        "결제 상태 변경 성공", HttpStatus.OK.value()
    );
  }

  @PatchMapping("/reservation")
  public BaseResponse<PaymentResponseDto> updatePaymentStatusByReservationId(
      @RequestParam UUID reservationId, @RequestBody UpdatePaymentRequestDto request) {

    UpdatePaymentRequestInternalDto internalDto = PaymentDtoMapper.toUpdateInternalDto(request);

    PaymentResponseInternalDto response = paymentService
        .updatePaymentStatusByReservationId(reservationId, internalDto);

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponse(response),
        "결제 상태 변경 성공", HttpStatus.OK.value()
    );
  }

  @DeleteMapping("/{paymentId}")
  public BaseResponse<PaymentResponseDto> deletePayment(@PathVariable UUID paymentId) {

    PaymentResponseInternalDto response = paymentService.deletePaymentById(paymentId);

    return BaseResponse.SUCCESS(
        PaymentDtoMapper.toResponse(response),
        "결제 삭제 성공", HttpStatus.OK.value()
    );
  }
}
