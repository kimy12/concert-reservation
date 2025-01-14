package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.DEDUCT;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcertFacade {

    private final ConcertService concertService;
    private final ReservationService reservationService;
    private final UserPointService userPointService;

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
        ConcertSeatModel seatInfo = concertService.findSeatInfo(request.seatId(), request.scheduleId());
        ReservationModel reservedSeat = reservationService.reserveSeat(request.userId(), seatInfo);
        return ConcertResponse.ReservedSeatInfo.builder()
                .createAt(reservedSeat.getCreatedAt())
                .price(reservedSeat.getPrice())
                .seatNumber(reservedSeat.getSeatNumber())
                .build();
    }

    @Transactional
    public ConcertResponse.ReservedSeatInfo payReservedSeat(ConcertRequest.ReserveConcert request) {

        ReservationModel reservedSeat = reservationService.findByReservedIdByCreatedAt(request.reservedId())
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));

        UserPoint userPoint = userPointService.chargeOrDeductPoint(request.userId(), reservedSeat.getPrice(), DEDUCT);

        ReservationModel reservedInfo = reservationService.reservedSeatComplete(reservedSeat);

        return ConcertResponse.ReservedSeatInfo.builder()
                .createAt(reservedInfo.getCreatedAt())
                .price(reservedInfo.getPrice())
                .seatNumber(reservedInfo.getSeatNumber())
                .build();
    }
}
