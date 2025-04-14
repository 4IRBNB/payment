package com.fourirbnb.payment.presentation.controller;

import com.fourirbnb.payment.application.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/payments")
public class PaymentInternalController {

  private final PaymentService paymentService;
}
