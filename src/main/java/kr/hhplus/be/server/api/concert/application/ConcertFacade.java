package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.common.exception.CustomException;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertInfoDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertScheduleDto;
import kr.hhplus.be.server.api.concert.domain.dto.ConcertSeatDto;
import kr.hhplus.be.server.api.concert.domain.dto.ReservationDto;
import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.point.domain.dto.UserPoint;
import kr.hhplus.be.server.api.point.domain.enums.PointHistoryType;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.RESERVED;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.CHARGE;
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
                .map(ConcertInfoDto::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ConcertResponse.AvailableDates> getAvailableDates(long concertId) {
        return concertService.findAvailableDates(concertId)
                .stream()
                .map(ConcertScheduleDto:: toResponseDto)
                .collect(Collectors.toList());
    }

    public List<ConcertResponse.SeatInfo> getAvailableSeats(long concertId) {
        return concertService.findAvailableSeats(concertId)
                .stream()
                .map(ConcertSeatDto:: toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ConcertResponse.ReservedSeatInfo reservedSeat(ConcertRequest.ReserveConcert request) {

       reservationService.findReservedSeatById(request.seatId()).ifPresent(reservedSeat -> {
           throw new CustomException(SEAT_NOT_AVAILABLE);
       });

        ReservationDto seatInfo = ReservationDto.builder()
                .seatId(request.seatId())
                .userId(request.userId())
                .scheduleId(request.scheduleId())
                .build();

        ReservationDto reservedSeat = reservationService.reserveSeat(seatInfo);
        return ConcertResponse.ReservedSeatInfo.builder()
                .createAt(reservedSeat.getCreatedAt())
                .price(reservedSeat.getPrice())
                .seatNumber(reservedSeat.getSeatNumber())
                .build();
    }

    @Transactional
    public ConcertResponse.ReservedSeatInfo payReservedSeat(ConcertRequest.ReserveConcert request) {

        ReservationDto reservedSeat = reservationService.findByReservedIdByCreatedAt(request.reservedId())
                .orElseThrow(() -> new CustomException(SEAT_NOT_AVAILABLE));

        UserPoint userPoint = userPointService.chargeOrDeductPoint(request.userId(), reservedSeat.getPrice(), DEDUCT);

        ReservationDto reservedInfo = reservationService.reservedSeatComplete(reservedSeat);

        return ConcertResponse.ReservedSeatInfo.builder()
                .createAt(reservedInfo.getCreatedAt())
                .price(reservedInfo.getPrice())
                .seatNumber(reservedInfo.getSeatNumber())
                .build();
    }
}
