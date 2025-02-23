package kr.hhplus.be.server.api.concert.application.event;

import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;

import java.time.LocalDateTime;


public record ReservationEvent(long reservationId,
                               long userId,
                               long scheduleId,
                               long seatNumber,
                               LocalDateTime createdAt,
                               long price) {

    public ReservationEvent(ReservationModel reservation) {
        this(reservation.getId(),
                reservation.getUserId(),
                reservation.getScheduleId(),
                reservation.getSeatNumber(),
                reservation.getCreatedAt(),
                reservation.getPrice());
    }
}
