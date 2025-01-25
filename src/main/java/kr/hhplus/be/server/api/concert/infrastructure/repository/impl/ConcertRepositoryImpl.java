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
import kr.hhplus.be.server.api.concert.infrastructure.repository.queryRepository.projection.SeatInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus.PENDING;
import static kr.hhplus.be.server.api.concert.domain.enums.ConcertSeatStatus.VOIDED;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertInfoQueryRepository concertInfoQueryRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    @Override
    public List<ConcertInfoModel> getConcertInfo(long concertId) {
        return concertInfoQueryRepository.getAllConcertInfos(concertId)
                .stream()
                .map(ConcertInfoProjection::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertScheduleModel> getAvailableDates(long concertId) {
        return concertInfoQueryRepository.getAllAvailableDates(concertId)
                .stream()
                .map(AvailableDatesProjection::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertSeatModel> getAvailableSeats(long scheduleId) {
        return concertInfoQueryRepository.getAllAvailableSeats(scheduleId)
                .stream()
                .map(AvailableSeatsProjection::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConcertSeatModel> findBySeatNumberAndScheduleIdAndStatus(Long seatNumber, Long scheduleId) {
        return concertSeatJpaRepository.findBySeatNumberAndScheduleIdAndStatus(seatNumber, scheduleId, VOIDED)
                .map(ConcertSeat :: toModel);
    }

    @Override
    public List<ConcertSeatModel> getAllSeatsForScheduler() {
        return concertInfoQueryRepository.getAllSeats()
                .stream()
                .map(SeatInfoProjection::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConcertSeatModel> findSeatInfoBySeatId(Long seatId) {
        return concertSeatJpaRepository.findById(seatId)
                .map(ConcertSeat :: toModel);
    }

    @Override
    public ConcertSeatModel saveSeat(ConcertSeatModel seat) {
        return concertSeatJpaRepository.save(seat.toEntity()).toModel();
    }
}
