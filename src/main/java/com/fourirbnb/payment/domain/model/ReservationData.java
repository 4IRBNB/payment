package com.fourirbnb.payment.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationData(UUID id, Long userId, UUID lodeId, Long price,
                                     LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                     String reservationStatus) {

}