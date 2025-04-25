package com.fourirbnb.payment.domain.port;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.payment.domain.model.ReservationData;
import com.fourirbnb.payment.infrastructure.client.dto.ReservationResponseDto;
import java.util.UUID;

public interface ReservationPort {

  BaseResponse<ReservationResponseDto> updateReservationStatus(
      UUID reservationId, String reservationStatus);

  ReservationData toDomainModel(BaseResponse<ReservationResponseDto> response);
}
