package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.dto.ReservationDto;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository {

    ReservationDto save(Reservation Reservation);

    Optional<ReservationDto> findBySeatId(Long id);

    Optional<ReservationDto>findReservedSeatByUserId(Long userId);

    Optional<ReservationDto> updateReservation(ReservationDto reservedSeat);

    Optional<ReservationDto> findById(Long reservedId);
}
