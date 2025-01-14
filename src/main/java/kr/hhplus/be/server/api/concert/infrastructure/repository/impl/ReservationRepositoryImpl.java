package kr.hhplus.be.server.api.concert.infrastructure.repository.impl;

import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public ReservationModel save(Reservation Reservation) {
        return reservationJpaRepository.save(Reservation).toDto();
    }

    @Override
    public Optional<ReservationModel> findReservedSeatByUserId(Long userId) {
        return reservationJpaRepository.findByUserId(userId)
                .map(Reservation::toDto);
    }

    @Override
    public Optional<ReservationModel> updateReservation(ReservationModel reservedSeat) {
        return reservationJpaRepository.updateStatusById(reservedSeat.getId(), reservedSeat.getStatus(), reservedSeat.getUpdatedAt())
                .map(Reservation :: toDto);
    }

    @Override
    public Optional<ReservationModel> findById(Long reservedId) {
        return reservationJpaRepository.findById(reservedId)
                .map(Reservation :: toDto);
    }
}
