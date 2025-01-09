package kr.hhplus.be.server.api.concert.infrastructure.repository.impl;

import kr.hhplus.be.server.api.concert.domain.dto.ConcertInfoDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertScheduleDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertSeatDto;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.ConcertInfoQueryRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.AvailableDatesProjection;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.AvailableSeatsProjection;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.ConcertInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertInfoQueryRepository concertInfoQueryRepository;

    @Override
    public List<ConcertInfoDto> getConcertInfo(long concertId) {
        return concertInfoQueryRepository.getAllConcertInfos(concertId)
                .stream()
                .map(ConcertInfoProjection::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertScheduleDto> getAvailableDates(long concertId) {
        return concertInfoQueryRepository.getAllAvailableDates(concertId)
                .stream()
                .map(AvailableDatesProjection::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSeatDto> getAvailableSeats(long scheduleId) {
        return concertInfoQueryRepository.getAllAvailableSeats(scheduleId)
                .stream()
                .map(AvailableSeatsProjection::toDto)
                .collect(Collectors.toList());
    }
}
