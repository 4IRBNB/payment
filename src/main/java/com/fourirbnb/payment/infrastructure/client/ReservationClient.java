package com.fourirbnb.payment.infrastructure.client;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.payment.config.FeignConfig;
import com.fourirbnb.payment.infrastructure.client.dto.ReservationResponseDto;
import com.fourirbnb.payment.infrastructure.client.dto.UpdateReservationDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reservation-service",
    url = "http://localhost:19095",
    configuration = FeignConfig.class)
public interface ReservationClient {

  @PatchMapping("/api/reservations/{reservationId}")
  BaseResponse<ReservationResponseDto> updateReservationStatus(
      @PathVariable UUID reservationId, @RequestBody UpdateReservationDto request);


}
