package kr.hhplus.be.server.api.concert.domain.repository;


import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository {

    List<ConcertInfoModel> getConcertInfo (long concertId);

    List<ConcertScheduleModel> getAvailableDates (long concertId);

    List<ConcertSeatModel> getAvailableSeats (long scheduleId);

    Optional<ConcertSeatModel> findBySeatNumberAndScheduleIdAndStatus(Long seatNumber, Long scheduleId);

    List<ConcertSeatModel> getAllSeatsForScheduler();

    Optional<ConcertSeatModel> findSeatInfoBySeatId(Long seatId);

    ConcertSeatModel saveSeat(ConcertSeatModel seat);

}
