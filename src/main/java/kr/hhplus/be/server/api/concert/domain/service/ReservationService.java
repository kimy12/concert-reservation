package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.repository.ReservationRepository;
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

    public ReservationModel reserveSeat(Long userId , ConcertSeatModel concertSeatModel) {
        ReservationModel seatInfoForReservation =
                new ReservationModel(userId,
                        concertSeatModel.getSeatId(),
                        concertSeatModel.getSeatNumber(),
                        concertSeatModel.getScheduleId(),
                        concertSeatModel.getPrice());
        return reservationRepository.save(seatInfoForReservation.toEntity());
    }

    public Optional<ReservationModel> updateReservation(ReservationModel reservedSeat) {
        return reservationRepository.updateReservation(reservedSeat);
    }

    public Optional<ReservationModel> findByReservedIdByCreatedAt(long reservedId) {

        LocalDateTime createdAt = reservationRepository.findById(reservedId)
                .map(ReservationModel::getCreatedAt)
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));

        LocalDateTime expiredDate = createdAt.plusMinutes(10);
        LocalDateTime now = LocalDateTime.now();

        if(expiredDate.isBefore(now)) {
            throw new CustomException(SEAT_NOT_AVAILABLE);
        }
        return reservationRepository.findById(reservedId);
    }

    public ReservationModel reservedSeatComplete(ReservationModel reservedSeat) {
        reservedSeat.setStatus(RESERVED);
        return reservationRepository.updateReservation(reservedSeat)
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));
    }
}
