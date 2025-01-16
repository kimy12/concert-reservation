package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.RESERVED;
import static kr.hhplus.be.server.api.concert.domain.enums.error.SeatErrorCode.SEAT_NOT_AVAILABLE;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationModel reserveSeat(Long userId , ConcertSeatModel concertSeatModel) {
        ReservationModel seatInfoForReservation =
                new ReservationModel(userId,
                        concertSeatModel.getSeatId(),
                        concertSeatModel.getSeatNumber(),
                        concertSeatModel.getScheduleId(),
                        concertSeatModel.getPrice());
        return reservationRepository.save(seatInfoForReservation.toEntity());
    }
    
    public ReservationModel findByReservedId (long reservedId, LocalDateTime now) {
        ReservationModel reservedSeat = reservationRepository.findById(reservedId)
                .orElseThrow(()-> new CustomException(SEAT_NOT_AVAILABLE));
        reservedSeat.checkCreatedAt(now);
        return reservedSeat;
    }

    public void reservedSeatComplete(ReservationModel reservedSeat) {
        reservedSeat.setStatus(RESERVED);
        reservationRepository.updateReservation(reservedSeat);
    }
}
