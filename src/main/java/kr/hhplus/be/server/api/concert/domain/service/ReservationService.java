package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.dto.ReservationDto;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.SEAT_NOT_AVAILABLE;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationDto reserveSeat(ReservationDto reservationDto) {
        return reservationRepository.save(reservationDto.toEntity());
    }

    public Optional<ReservationDto> findReservedSeatById(Long seatId){
        return reservationRepository.findBySeatId(seatId);
    }

    public Optional<ReservationDto> findReservedSeatByUserId(Long userId){
        return reservationRepository.findReservedSeatByUserId(userId);
    }

    public Optional<ReservationDto> updateReservation(ReservationDto reservedSeat) {
        return reservationRepository.updateReservation(reservedSeat);
    }
}
