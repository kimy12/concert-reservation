package kr.hhplus.be.server.api.concert.domain.repository;

import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    ReservationModel save (Reservation Reservation);

    Optional<ReservationModel>findReservedSeatByUserId(Long userId);

    int updateReservation(ReservationModel reservedSeat);

    Optional<ReservationModel> findByIdAndStatus(Long reservedId);
}
