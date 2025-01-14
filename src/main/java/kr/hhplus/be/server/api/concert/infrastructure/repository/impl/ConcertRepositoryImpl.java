package kr.hhplus.be.server.api.concert.infrastructure.repository.impl;

import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.repository.ConcertRepository;
import kr.hhplus.be.server.api.concert.infrastructure.entity.ConcertSeat;
import kr.hhplus.be.server.api.concert.infrastructure.repository.jpaRepository.ConcertSeatJpaRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.ConcertInfoQueryRepository;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.AvailableDatesProjection;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.AvailableSeatsProjection;
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.ConcertInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertInfoQueryRepository concertInfoQueryRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    @Override
    public List<ConcertInfoModel> getConcertInfo(long concertId) {
        return concertInfoQueryRepository.getAllConcertInfos(concertId)
                .stream()
                .map(ConcertInfoProjection::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertScheduleModel> getAvailableDates(long concertId) {
        return concertInfoQueryRepository.getAllAvailableDates(concertId)
                .stream()
                .map(AvailableDatesProjection::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSeatModel> getAvailableSeats(long scheduleId) {
        return concertInfoQueryRepository.getAllAvailableSeats(scheduleId)
                .stream()
                .map(AvailableSeatsProjection::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConcertSeatModel> findBySeatNumberAndScheduleId(Long seatNumber, Long scheduleId) {
        return concertSeatJpaRepository.findBySeatNumberAndScheduleId(seatNumber, scheduleId)
                .map(ConcertSeat :: toModel);
    }
}
