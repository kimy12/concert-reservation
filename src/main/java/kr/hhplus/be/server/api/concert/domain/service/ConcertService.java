package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertRepository;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertInfoModel> findConcertInfoById(long concertId) {
        return Optional.ofNullable(concertRepository.getConcertInfo(concertId))
                .orElseThrow(() -> new CustomException(CONCERT_NOT_FOUND))
                .stream()
                .toList();
    }

    public List<ConcertScheduleModel> findAvailableDates(long concertId) {
        return Optional.ofNullable(concertRepository.getAvailableDates(concertId))
                .orElseThrow(() -> new CustomException(RESERVATION_DATE_NOT_FOUND))
                .stream()
                .toList();
    }

    public List<ConcertSeatModel> findAvailableSeats(long concertId) {
        return Optional.ofNullable(concertRepository.getAvailableSeats(concertId))
                .orElseThrow(() -> new CustomException(RESERVATION_SEAT_NOT_FOUND))
                .stream()
                .toList();
    }

    public ConcertSeatModel findSeatInfo(Long seatId, Long scheduleId){
        return concertRepository.findBySeatNumberAndScheduleId(seatId, scheduleId)
                .orElseThrow(
                        ()-> new CustomException(SEAT_NOT_AVAILABLE)
                );
    }
}
