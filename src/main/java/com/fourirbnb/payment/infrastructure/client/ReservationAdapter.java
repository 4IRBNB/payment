package com.fourirbnb.payment.infrastructure.client;

import com.fourirbnb.common.response.BaseResponse;
import com.fourirbnb.payment.domain.model.ReservationData;
import com.fourirbnb.payment.domain.port.ReservationPort;
import com.fourirbnb.payment.infrastructure.client.dto.ReservationResponseDto;
import com.fourirbnb.payment.infrastructure.client.dto.UpdateReservationDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationAdapter implements ReservationPort {

  private final ReservationClient reservationClient;

  @Override
  public BaseResponse<ReservationResponseDto> updateReservationStatus(
      UUID reservationId, String reservationStatus) {

    UpdateReservationDto request = new UpdateReservationDto(reservationStatus);

    return reservationClient.updateReservationStatus(reservationId, request);
  }

  @Override
  public ReservationData toDomainModel(BaseResponse<ReservationResponseDto> response) {

    ReservationResponseDto dto = response.getData();

    return new ReservationData(
        dto.id(), dto.userId(), dto.lodeId(),
        dto.price(), dto.checkInDate(), dto.checkOutDate(),
        dto.reservationStatus()
    );
  }
}
