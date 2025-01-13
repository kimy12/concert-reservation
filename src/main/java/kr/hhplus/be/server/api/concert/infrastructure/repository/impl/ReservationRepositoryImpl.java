package kr.hhplus.be.server.api.concert.infrastructure.repository.impl;

import kr.hhplus.be.server.api.concert.domain.dto.ReservationDto;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import kr.hhplus.be.server.api.concert.infrastructure.entity.Reservation;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public ReservationDto save(Reservation Reservation) {
        return reservationJpaRepository.save(Reservation).toDto();
    }

    @Override
    public Optional<ReservationDto> findBySeatId(Long id) {
        return reservationJpaRepository.findBySeatId(id)
                .map(Reservation::toDto);
    }

    @Override
    public Optional<ReservationDto> findReservedSeatByUserId(Long userId) {
        return reservationJpaRepository.findByUserId(userId)
                .map(Reservation::toDto);
    }

    @Override
    public Optional<ReservationDto> updateReservation(ReservationDto reservedSeat) {
        return reservationJpaRepository.updateStatusById(reservedSeat.getId(), reservedSeat.getStatus(), reservedSeat.getUpdatedAt())
                .map(Reservation :: toDto);
    }

    @Override
    public Optional<ReservationDto> findById(Long reservedId) {
        return reservationJpaRepository.findById(reservedId)
                .map(Reservation :: toDto);
    }
}
