package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.application.event.AfterReservationConfirmedEvent;
import kr.hhplus.be.server.api.concert.application.event.publisher.ReservationPublisher;
import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import kr.hhplus.be.server.api.concert.infrastructure.aop.RedissonLock;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kr.hhplus.be.server.api.concert.domain.enums.error.SeatErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.DEDUCT;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertFacade {

    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final UserPointService userPointService;
    private final ApplicationEventPublisher eventPublisher;
    private final ReservationPublisher reservationPublisher;

    public List<ConcertResponse.ConcertInfo> getConcertInfo(long concertId) {
        return concertService.findConcertInfoById(concertId)
                .stream()
                .map(ConcertInfoModel::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ConcertResponse.AvailableDates> getAvailableDates(long concertId) {
        return concertService.findAvailableDates(concertId)
                .stream()
                .map(ConcertScheduleModel:: toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ConcertResponse.SeatInfo> getAvailableSeats(long concertId) {
        return concertService.findAvailableSeats(concertId)
                .stream()
                .map(ConcertSeatModel:: toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ConcertResponse.ReservedSeatInfo reservedSeat(ConcertRequest.ReserveConcert request) {
        try {
            ConcertSeatModel seatInfo = concertService.changeSeatStatusToPending(request.seatId(), request.scheduleId());
            ReservationModel reservedSeat = reservationService.reserveSeat(request.userId(), seatInfo);
            reservationPublisher.publish(reservedSeat);
            return new ConcertResponse.ReservedSeatInfo(reservedSeat.getScheduleId(), reservedSeat.getSeatNumber(), reservedSeat.getPrice(), reservedSeat.getCreatedAt());
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new CustomException(SEAT_NOT_AVAILABLE);
        }
    }

    @RedissonLock(key = "#request.reservedId()")
    @Transactional
    public ConcertResponse.ReservedSeatInfo payReservedSeat(ConcertRequest.PayReserveConcert request) {
        LocalDateTime now = LocalDateTime.now();
        ReservationModel reservedSeat = reservationService.findByReservedId(request.reservedId(), now);
        userPointService.chargeOrDeductPoint(request.userId(), reservedSeat.getPrice(), DEDUCT);
        reservationService.reservedSeatComplete(reservedSeat);

        eventPublisher.publishEvent(new AfterReservationConfirmedEvent(
                request.userId(),
                reservedSeat.getScheduleId(),
                reservedSeat.getSeatNumber(),
                reservedSeat.getPrice()
        ));

        return new ConcertResponse.ReservedSeatInfo(reservedSeat.getScheduleId(), reservedSeat.getSeatNumber(), reservedSeat.getPrice(), reservedSeat.getCreatedAt());
    }
}
