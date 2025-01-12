package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.dto.ReservationDto;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.RESERVED;


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

    public Optional<ReservationDto> findByReservedIdByCreatedAt(long reservedId) {

        LocalDateTime createdAt = reservationRepository.findById(reservedId)
                .map(ReservationDto::getCreatedAt)
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));

        LocalDateTime expiredDate = createdAt.plusMinutes(10);
        LocalDateTime now = LocalDateTime.now();

        if(expiredDate.isBefore(now)) {
            throw new CustomException(SEAT_NOT_AVAILABLE);
        }
        return reservationRepository.findById(reservedId);
    }

    public ReservationDto reservedSeatComplete(ReservationDto reservedSeat) {
        reservedSeat.setStatus(RESERVED);
        return reservationRepository.updateReservation(reservedSeat)
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));
    }
}
