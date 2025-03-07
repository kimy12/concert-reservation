package kr.hhplus.be.server.api.concert.domain.service;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.hhplus.be.server.api.concert.domain.enums.error.ConcertErrorCode.CONCERT_NOT_FOUND;
import static kr.hhplus.be.server.api.concert.domain.enums.error.ReservationErrorCode.RESERVATION_DATE_NOT_FOUND;
import static kr.hhplus.be.server.api.concert.domain.enums.error.ReservationErrorCode.RESERVATION_SEAT_NOT_FOUND;
import static kr.hhplus.be.server.api.concert.domain.enums.error.SeatErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.concert.domain.enums.error.SeatErrorCode.SEAT_NOT_FOUND;

@Slf4j
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
        return concertRepository.findBySeatNumberAndScheduleIdAndStatus(seatId, scheduleId)
                .orElseThrow(
                        ()-> new CustomException(SEAT_NOT_AVAILABLE)
                );
    }

    public ConcertSeatModel changeSeatStatusToPending (Long seatId, Long scheduleId){
        return concertRepository.findBySeatNumberAndScheduleIdAndStatus(seatId, scheduleId)
                .map(seatModel -> {
                    seatModel.turnToPending();
                    return concertRepository.saveSeat(seatModel);
                })
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));
    }

    /**
     * 스케줄러 :: 선점되어있는 자리 리스트
     */
    public List<ConcertSeatModel> findSeatInfo (){
        return concertRepository.getAllSeatsForScheduler();
    }

    /**
     * 스케줄러 :: 자리 상태 변경
     */
    @Transactional
    public void changeSeatStatus(Long seatId){
        ConcertSeatModel seatInfo = concertRepository.findSeatInfoBySeatId(seatId)
                .orElseThrow(() -> new CustomException(SEAT_NOT_FOUND));
        seatInfo.turnToVoided();
    }
}
