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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.POINT_INSUFFICIENT;
import static kr.hhplus.be.server.api.common.exception.enums.ErrorCode.SEAT_NOT_AVAILABLE;
import static kr.hhplus.be.server.api.concert.domain.enums.ReservationStatus.RESERVED;
import static kr.hhplus.be.server.api.point.domain.enums.PointHistoryType.DEDUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertFacadeTest {
    @InjectMocks
    private ConcertFacade concertFacade;

    @Mock
    private ConcertService concertService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private UserPointService userPointService;

    @DisplayName("콘서트 정보를 조회한다.")
    @Test
    void getConcertInfo() {
        // given
        long concertId = 1L;
        List<ConcertInfoDto> mockConcertInfo = List.of(
                new ConcertInfoDto(concertId, "Concert A", 1L, 1, null),
                new ConcertInfoDto(concertId, "Concert B", 2L, 2, null)
        );
        when(concertService.findConcertInfoById(concertId)).thenReturn(mockConcertInfo);

        // when
        List<ConcertResponse.ConcertInfo> result = concertFacade.getConcertInfo(concertId);

        // then
        assertThat(result).hasSize(2);
        verify(concertService, times(1)).findConcertInfoById(concertId);
    }

    @DisplayName("예약 가능한 날짜를 조회한다.")
    @Test
    void getAvailableDates() {
        // given
        long concertId = 1L;
        List<ConcertScheduleDto> mockSchedules = List.of(
                new ConcertScheduleDto(concertId, "title1",1L, 1, null),
                new ConcertScheduleDto(concertId, "title1",2L, 2, null)
        );
        when(concertService.findAvailableDates(concertId)).thenReturn(mockSchedules);

        // when
        List<ConcertResponse.AvailableDates> result = concertFacade.getAvailableDates(concertId);

        // then
        assertThat(result).hasSize(2);
        verify(concertService, times(1)).findAvailableDates(concertId);
    }

    @DisplayName("예약 가능한 좌석을 조회한다.")
    @Test
    void getAvailableSeats() {
        // given
        long concertId = 1L;
        List<ConcertSeatDto> mockSeats = List.of(
                new ConcertSeatDto(1L, 1L, 1L, 10000L, 1, null),
                new ConcertSeatDto(2L, 2L, 1L, 12000L, 2, null)
        );
        when(concertService.findAvailableSeats(concertId)).thenReturn(mockSeats);

        // when
        List<ConcertResponse.SeatInfo> result = concertFacade.getAvailableSeats(concertId);

        // then
        assertThat(result).hasSize(2);
        verify(concertService, times(1)).findAvailableSeats(concertId);
    }

    @DisplayName("좌석을 예약한다.")
    @Test
    void reservedSeat() {
        // given
        ConcertRequest.ReserveConcert request = new ConcertRequest.ReserveConcert(1L, 2L, 3L, 3L);
        when(reservationService.findReservedSeatById(request.seatId())).thenReturn(Optional.empty());

        ReservationDto mockReservedSeat = ReservationDto.builder()
                .seatId(1L)
                .userId(2L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .createdAt(LocalDateTime.now())
                .build();
        when(reservationService.reserveSeat(any())).thenReturn(mockReservedSeat);

        // when
        ConcertResponse.ReservedSeatInfo result = concertFacade.reservedSeat(request);

        // then
        assertThat(result.price()).isEqualTo(mockReservedSeat.getPrice());
        verify(reservationService, times(1)).findReservedSeatById(request.seatId());
        verify(reservationService, times(1)).reserveSeat(any());
    }

    @DisplayName("이미 예약된 좌석일 경우 예외를 던진다.")
    @Test
    void reservedSeatThrowsException() {

        // given
        ReservationDto mockReservedSeat = ReservationDto.builder()
                .seatId(1L)
                .userId(2L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .createdAt(LocalDateTime.now())
                .build();
        ConcertRequest.ReserveConcert request = new ConcertRequest.ReserveConcert(1L, 2L, 3L, 1L);
        when(reservationService.findReservedSeatById(request.seatId())).thenReturn(Optional.ofNullable(mockReservedSeat));

        // when & then
        assertThatThrownBy(() -> concertFacade.reservedSeat(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(SEAT_NOT_AVAILABLE.getMessage());

        verify(reservationService, times(1)).findReservedSeatById(request.seatId());
        verify(reservationService, times(0)).reserveSeat(any());
    }

    @DisplayName("좌석을 결제한다.")
    @Test
    void payReservedSeat() {
        // given
        ConcertRequest.ReserveConcert request = new ConcertRequest.ReserveConcert(1L, 2L, 3L, 2L);

        ReservationDto mockReservedSeat = ReservationDto.builder()
                .seatId(1L)
                .userId(2L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .createdAt(LocalDateTime.now())
                .build();

        UserPoint mockUserPoint = UserPoint.builder()
                .userId(2L)
                .totalPoint(30000L)
                .build();

        ReservationDto updatedReservation = ReservationDto.builder()
                .seatId(1L)
                .userId(2L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .status(RESERVED)
                .createdAt(LocalDateTime.now())
                .build();

        when(reservationService.findReservedSeatById(request.seatId())).thenReturn(Optional.of(mockReservedSeat));
        when(userPointService.chargeOrDeductPoint(request.userId(), mockReservedSeat.getPrice(), DEDUCT)).thenReturn(mockUserPoint);
        when(reservationService.updateReservation(mockReservedSeat)).thenReturn(Optional.of(updatedReservation));

        // when
        ConcertResponse.ReservedSeatInfo result = concertFacade.payReservedSeat(request);

        // then
        assertThat(result.price()).isEqualTo(updatedReservation.getPrice());
        assertThat(result.seatNumber()).isEqualTo(updatedReservation.getSeatNumber());
        verify(reservationService, times(1)).findReservedSeatById(request.seatId());
        verify(userPointService, times(1)).chargeOrDeductPoint(request.userId(), mockReservedSeat.getPrice(), DEDUCT);
        verify(reservationService, times(1)).updateReservation(mockReservedSeat);
    }

    @DisplayName("결제 시 좌석이 예약되지 않았을 경우 예외를 던진다.")
    @Test
    void payReservedSeatThrowsExceptionForUnavailableSeat() {
        // given
        ConcertRequest.ReserveConcert request = new ConcertRequest.ReserveConcert(1L, 2L, 3L,10);
        when(reservationService.findReservedSeatById(request.seatId())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> concertFacade.payReservedSeat(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(SEAT_NOT_AVAILABLE.getMessage());

        verify(reservationService, times(1)).findReservedSeatById(request.seatId());
        verify(userPointService, times(0)).chargeOrDeductPoint(anyLong(), anyLong(), any(PointHistoryType.class));
        verify(reservationService, times(0)).updateReservation(any());
    }

    @DisplayName("결제 시 포인트 부족으로 예외를 던진다.")
    @Test
    void payReservedSeatThrowsExceptionForInsufficientPoints() {
        // given
        ConcertRequest.ReserveConcert request = new ConcertRequest.ReserveConcert(1L, 2L, 3L, 11);

        ReservationDto mockReservedSeat = ReservationDto.builder()
                .seatId(1L)
                .userId(2L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .createdAt(LocalDateTime.now())
                .build();

        when(reservationService.findReservedSeatById(request.seatId())).thenReturn(Optional.of(mockReservedSeat));
        when(userPointService.chargeOrDeductPoint(request.userId(), mockReservedSeat.getPrice(), DEDUCT))
                .thenThrow(new CustomException(POINT_INSUFFICIENT));

        // when & then
        assertThatThrownBy(() -> concertFacade.payReservedSeat(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("포인트가 부족합니다.");

        verify(reservationService, times(1)).findReservedSeatById(request.seatId());
        verify(userPointService, times(1)).chargeOrDeductPoint(request.userId(), mockReservedSeat.getPrice(), DEDUCT);
        verify(reservationService, times(0)).updateReservation(any());
    }

}