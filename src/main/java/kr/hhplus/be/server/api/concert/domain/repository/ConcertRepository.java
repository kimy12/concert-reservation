package kr.hhplus.be.server.api.concert.domain.repository;


import kr.hhplus.be.server.api.concert.domain.dto.ConcertInfoDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertScheduleDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertSeatDto;

import java.util.List;

public interface ConcertRepository {

    List<ConcertInfoDto> getConcertInfo (long concertId);

    List<ConcertScheduleDto> getAvailableDates (long concertId);

    List<ConcertSeatDto> getAvailableSeats (long scheduleId);
}
