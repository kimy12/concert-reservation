package kr.hhplus.be.server.api.concert.application;

import kr.hhplus.be.server.api.concert.domain.model.ConcertInfoModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertScheduleModel;
import kr.hhplus.be.server.api.concert.domain.model.ConcertSeatModel;
import kr.hhplus.be.server.api.concert.domain.model.ReservationModel;
import kr.hhplus.be.server.api.concert.domain.service.ConcertService;
import kr.hhplus.be.server.api.concert.domain.service.ReservationService;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertRequest;
import kr.hhplus.be.server.api.concert.presentation.dto.ConcertResponse;
import kr.hhplus.be.server.api.point.domain.service.UserPointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
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
        List<ConcertInfoModel> mockConcertInfo = List.of(
                new ConcertInfoModel(concertId, "Concert A", 1L, 1, null),
                new ConcertInfoModel(concertId, "Concert B", 2L, 2, null)
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
        List<ConcertScheduleModel> mockSchedules = List.of(
                new ConcertScheduleModel(concertId, "title1",1L, 1, null),
                new ConcertScheduleModel(concertId, "title1",2L, 2, null)
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
        List<ConcertSeatModel> mockSeats = List.of(
                ConcertSeatModel.builder()
                        .seatId(1L)
                        .scheduleId(1L)
                        .concertId(1L)
                        .price(10000L)
                        .seatNumber(30)
                        .status(null)
                        .build(),
                ConcertSeatModel.builder()
                        .seatId(2L)
                        .scheduleId(2L)
                        .concertId(1L)
                        .price(12000L)
                        .seatNumber(31)
                        .status(null)
                        .build()
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
        ConcertRequest.ReserveConcert request = new ConcertRequest.ReserveConcert(1L, 2L, 3L);
        ReservationModel mockReservedSeat = ReservationModel.builder()
                .seatId(1L)
                .userId(2L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .createdAt(LocalDateTime.now())
                .build();

        ConcertSeatModel seatInfo = ConcertSeatModel.builder()
                                                    .seatId(1L)
                                                    .scheduleId(3L)
                                                    .concertId(1L)
                                                    .price(15000L)
                                                    .seatNumber(10L)
                                                    .status(null)
                                                    .build();

        when(concertService.findSeatInfo(request.seatId(), request.scheduleId())).thenReturn(seatInfo);
        when(reservationService.reserveSeat(request.userId(), seatInfo)).thenReturn(mockReservedSeat);

        // when
        ConcertResponse.ReservedSeatInfo result = concertFacade.reservedSeat(request);

        // then
        assertThat(result.price()).isEqualTo(mockReservedSeat.getPrice());
        verify(concertService, times(1)).findSeatInfo(request.seatId(), request.scheduleId());
        verify(reservationService, times(1)).reserveSeat(request.userId(), seatInfo);
    }

    @DisplayName("좌석을 결제한다.")
    @Test
    void payReservedSeat() {
        // given
        LocalDateTime now = LocalDateTime.of(2025, 1, 16, 12, 0); // 고정된 now 값
        ConcertRequest.PayReserveConcert request = new ConcertRequest.PayReserveConcert(1L, 3L, 1L, 5L);

        ReservationModel mockReservedSeat = ReservationModel.builder()
                .seatId(1L)
                .userId(1L)
                .scheduleId(3L)
                .seatNumber(10L)
                .price(15000L)
                .createdAt(now)
                .build();

        when(reservationService.findByReservedId(eq(request.reservedId()), any(LocalDateTime.class)))
                .thenReturn(mockReservedSeat);

        // when
        ConcertResponse.ReservedSeatInfo result = concertFacade.payReservedSeat(request);

        // then
        assertThat(result.price()).isEqualTo(mockReservedSeat.getPrice());
        assertThat(result.seatNumber()).isEqualTo(mockReservedSeat.getSeatNumber());
    }
}